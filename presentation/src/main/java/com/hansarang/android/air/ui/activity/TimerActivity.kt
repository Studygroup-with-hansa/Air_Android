package com.hansarang.android.air.ui.activity

import android.app.NotificationManager
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.work.*
import com.hansarang.android.air.R
import com.hansarang.android.air.background.worker.TimerWorker
import com.hansarang.android.air.databinding.ActivityTimerBinding
import com.hansarang.android.air.di.assistedfactory.TimerAssistedFactory
import com.hansarang.android.air.ui.base.BaseActivity
import com.hansarang.android.air.ui.viewmodel.activity.TimerViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class TimerActivity : BaseActivity<ActivityTimerBinding, TimerViewModel>() {

    private lateinit var timerTask: TimerTask
    private lateinit var timer: Timer
    private lateinit var timerWorkerRequest: OneTimeWorkRequest
    @Inject lateinit var timerAssistedFactory: TimerAssistedFactory
    override val viewModel: TimerViewModel by viewModels {
        val date = System.currentTimeMillis()
        val title = intent.getStringExtra("title") ?: ""
        val time = intent.getLongExtra("time", 0L)
        val goal = intent.getLongExtra("goal", 0L)
        val isStarted = intent.getBooleanExtra("isStarted", false)

        TimerViewModel.provideFactory(
            timerAssistedFactory,
            date,
            title,
            time,
            goal,
            isStarted
        )
    }

    override fun onResume() {
        super.onResume()
        WorkManager.getInstance(this@TimerActivity).cancelUniqueWork(TimerWorker.TAG)
    }

    override fun observerViewModel() {
        WorkManager
            .getInstance(this@TimerActivity)
            .cancelAllWorkByTag(TimerWorker.TAG)
        notificationCancel()

        binding.tvTitleTimer.text = viewModel.title
        binding.tvDateTimer.text = resources.getString(R.string.ymd_format, viewModel.date)

        if (this@TimerActivity::timerTask.isInitialized) {
            timerTask.cancel()
        }

        viewModel.backButtonClick.observe(this) {
            activityFinish()
            viewModel.postTimerStop()
        }

        viewModel.isStarted.observe(this@TimerActivity) {
            binding.chkPlayButtonTimer.isChecked = true
            timer = Timer()
            timerTask =
                timerTask {
                    viewModel.time.postValue(
                        (viewModel.time.value?:0) + 1L
                    )
                }
            viewModel.postTimerStart()
            timer.schedule(timerTask, 1000L, 1000L)
        }

        viewModel.isStopped.observe(this@TimerActivity) {
            binding.chkPlayButtonTimer.isChecked = false
            if (this@TimerActivity::timerTask.isInitialized) {
                activityFinish()
                timerTask.cancel()
            }

            timer.cancel()
            viewModel.postTimerStop()

            notificationCancel()
        }
    }

    override fun onStop(): Unit = with(viewModel) {
        super.onStop()

        if (binding.chkPlayButtonTimer.isChecked) {
            val timerData = Data.Builder()
                .putLong("totalTime", time.value!!)
                .putLong("goal", goal)
                .putString("title", title)
                .putBoolean("isStarted", true)
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

    private fun notificationCancel() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(1)
    }
}