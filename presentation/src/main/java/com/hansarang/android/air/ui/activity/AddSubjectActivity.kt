package com.hansarang.android.air.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivityAddSubjectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSubjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSubjectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        binding = ActivityAddSubjectBinding.inflate(inflater)
        setContentView(binding.root)

        init()
        observe()
        listener()
    }

    private fun init() {

    }

    private fun observe() {

    }

    private fun listener() = with(binding) {
        toolbarAddSubject.setNavigationOnClickListener {
            finish()
        }

        btnAddSubject.setOnClickListener {

        }
    }
}