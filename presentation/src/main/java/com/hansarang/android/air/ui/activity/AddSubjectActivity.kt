package com.hansarang.android.air.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivityAddSubjectBinding
import com.hansarang.android.air.ui.adapter.ColorPickerAdapter
import com.hansarang.android.air.ui.decorator.GridDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSubjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSubjectBinding
    private lateinit var colorPickerAdapter: ColorPickerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        binding = ActivityAddSubjectBinding.inflate(inflater)
        setContentView(binding.root)

        init()
        observe()
        listener()
    }

    private fun init() = with(binding) {
        colorPickerAdapter = ColorPickerAdapter()
        rvColorPickerAddSubject.adapter = colorPickerAdapter
        rvColorPickerAddSubject.addItemDecoration(GridDividerDecorator(10.dp, 5, false))
        colorPickerAdapter.submitList(colorPickerAdapter.colorList)
    }

    private fun observe() {

    }

    private fun listener() = with(binding) {
        toolbarAddSubject.setNavigationOnClickListener {
            finish()
        }

        btnAddSubject.setOnClickListener {
            finish()
        }
    }
}