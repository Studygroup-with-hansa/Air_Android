package com.hansarang.android.air.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.hansarang.android.air.databinding.ActivityTimerBinding
import com.hansarang.android.air.ui.viewmodel.activity.TimerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimerBinding
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
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

}