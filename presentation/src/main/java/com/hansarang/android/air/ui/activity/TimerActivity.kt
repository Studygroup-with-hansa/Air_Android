package com.hansarang.android.air.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.work.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        initIntent()
        observe()
        listener()
    }

    private fun listener() = with(binding) {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun observe() = with(viewModel) {
        isStarted.observe(this@TimerActivity) {
            timer = Timer()
            if (it) {
                timerTask =
                    timerTask {
                        if (goal.value?:0L > time.value?:0L)
                            time.postValue((time.value?:0L) + 1L)
                        else isStarted.postValue(false)
                    }
                timer.schedule(timerTask, 1000L, 1000L)
            } else {
                timer.cancel()
                if (this@TimerActivity::timerTask.isInitialized) {
                    timerTask.cancel()
                }
            }
        }
    }

    private fun init() = with(viewModel) {
        binding = ActivityTimerBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this@TimerActivity
        setContentView(binding.root)
    }

    private fun initIntent() = with(viewModel) {
        title.value = intent.getStringExtra("title")
        date.value = System.currentTimeMillis()
        time.value = intent.getLongExtra("totalTime", TimerWorker.time)
        goal.value = intent.getLongExtra("goal", 0L)
        isStarted.value = intent.getBooleanExtra("isStarted", false)
        if (this@TimerActivity::timerTask.isInitialized) {
            timerTask.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        WorkManager.getInstance(this@TimerActivity).cancelAllWorkByTag(TimerWorker.TAG)
    }

    override fun onRestart(): Unit = with(viewModel) {
        super.onRestart()
        initIntent()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onDestroy(): Unit = with(viewModel) {
        super.onDestroy()

        if (isStarted.value == true) {
            val timerData = Data.Builder()
                .putLong("totalTime", time.value ?: 0L)
                .putLong("goal", goal.value ?: 0L)
                .putString("title", title.value)
                .putBoolean("isStarted", isStarted.value ?: false)
                .build()

            timerWorkerRequest = OneTimeWorkRequestBuilder<TimerWorker>()
                .setInputData(timerData)
                .addTag(TimerWorker.TAG)
                .build()

            WorkManager.getInstance(this@TimerActivity).enqueue(timerWorkerRequest)

//            val intent = Intent(this@TimerActivity, TimerService::class.java)
//            intent.putExtra("totalTime", time.value)
//            intent.putExtra("goal", goal.value)
//            intent.putExtra("title", title.value)
//            startForegroundService(intent)
        }
    }

}