package com.hansarang.android.air.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hansarang.android.air.background.service.TimerService
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
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observe()
    }

    private fun observe() = with(viewModel) {
        isStarted.observe(this@TimerActivity) {
            if (it) {
                timer = Timer()
                timerTask =
                    timerTask {
                        if (goal.value?:0L > time.value?:0L) time.postValue((time.value?:0L) + 1L)
                        else isStarted.postValue(false)
                    }
                timer.schedule(timerTask, 0L, 1000L)
            } else {
                timer.cancel()
            }
        }
    }

    private fun init() = with(viewModel) {
        binding = ActivityTimerBinding.inflate(layoutInflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this@TimerActivity
        setContentView(binding.root)

        title.value = intent.getStringExtra("title")
        date.value = System.currentTimeMillis()
        time.value = intent.getLongExtra("totalTime", 0L)
        goal.value = intent.getLongExtra("goal", 0L)
    }

    override fun onDestroy() = with(viewModel) {
        super.onDestroy()

        if (isStarted.value == true) {
            timer.cancel()
            val intent = Intent(this@TimerActivity, TimerService::class.java)
            intent.putExtra("totalTime", time.value)
            intent.putExtra("goal", goal.value)
            intent.putExtra("title", title.value)
            startForegroundService(intent)
        }
    }
}