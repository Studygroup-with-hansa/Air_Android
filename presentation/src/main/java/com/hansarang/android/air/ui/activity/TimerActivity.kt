package com.hansarang.android.air.ui.activity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.hansarang.android.air.R
import com.hansarang.android.air.background.service.ForegroundTimerForceQuitService
import com.hansarang.android.air.background.worker.TimerWorker
import com.hansarang.android.air.databinding.ActivityTimerBinding
import com.hansarang.android.air.ui.viewmodel.activity.TimerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class TimerActivity : AppCompatActivity() {

    private lateinit var timerTask: TimerTask
    private lateinit var timer: Timer
    private lateinit var binding: ActivityTimerBinding
    private lateinit var timerWorkerRequest: OneTimeWorkRequest
    private val viewModel: TimerViewModel by viewModels()

    private lateinit var foregroundIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        binding = ActivityTimerBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initIntent()
        observe()
        listener()
    }

    private fun listener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            activityFinish()
            viewModel.isStarted.value = false
        }
    }

    private fun observe() = with(viewModel) {
        isStarted.observe(this@TimerActivity) {
            timer = Timer()
            if (it) {
                timerTask =
                    timerTask {
                        time.postValue((time.value?:0L) + 1L)
                    }
                postTimerStart()
                timer.schedule(timerTask, 1000L, 1000L)
            } else {
                if (this@TimerActivity::timerTask.isInitialized) {
                    activityFinish()
                    timerTask.cancel()
                }

                timer.cancel()
                postTimerStop()

                notificationCancel(1)
            }
        }
    }

    private fun initIntent() = with(viewModel) {

        WorkManager
            .getInstance(this@TimerActivity)
            .cancelAllWorkByTag(TimerWorker.TAG)

        notificationCancel(1)

        date.value = System.currentTimeMillis()

        title.value = intent.getStringExtra("title")
        time.value = intent.getLongExtra("time", 0L)
        goal.value = intent.getLongExtra("goal", 0L)
        isStarted.value = intent.getBooleanExtra("isStarted", false)

        foregroundIntent = Intent(this@TimerActivity, ForegroundTimerForceQuitService::class.java)
        foregroundIntent.putExtra("title", title.value)
        startService(foregroundIntent)

        if (this@TimerActivity::timerTask.isInitialized) {
            timerTask.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        WorkManager.getInstance(this@TimerActivity).cancelUniqueWork(TimerWorker.TAG)
    }

    override fun onStop(): Unit = with(viewModel) {
        super.onStop()

        if (isStarted.value == true) {
            val timerData = Data.Builder()
                .putLong("totalTime", time.value ?: 0L)
                .putLong("goal", goal.value ?: 0L)
                .putString("title", title.value)
                .putBoolean("isStarted", isStarted.value ?: false)
                .putBoolean("onDestroyed", true)
                .build()

            timerWorkerRequest = OneTimeWorkRequestBuilder<TimerWorker>()
                .setInputData(timerData)
                .addTag(TimerWorker.TAG)
                .build()

            WorkManager.getInstance(this@TimerActivity).enqueueUniqueWork(TimerWorker.TAG, ExistingWorkPolicy.REPLACE, timerWorkerRequest)
        }
    }

    private fun activityFinish() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
    }

    private fun notificationCancel(id: Int) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(id)
    }
}