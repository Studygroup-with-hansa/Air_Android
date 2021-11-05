package com.hansarang.android.air.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.hansarang.android.air.databinding.ActivityAddSubjectBinding
import com.hansarang.android.air.ui.adapter.ColorPickerAdapter
import com.hansarang.android.air.ui.decorator.GridDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.activity.AddSubjectViewModel
import com.hansarang.android.domain.entity.dto.Subject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSubjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSubjectBinding
    private lateinit var colorPickerAdapter: ColorPickerAdapter
    private val viewModel: AddSubjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = LayoutInflater.from(this)
        binding = ActivityAddSubjectBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        init()
        observe()
        listener()
    }

    private fun init() = with(binding) {
        colorPickerAdapter = ColorPickerAdapter(viewModel)
        rvColorPickerAddSubject.adapter = colorPickerAdapter
        rvColorPickerAddSubject.addItemDecoration(GridDividerDecorator(10.dp, 5, false))
        colorPickerAdapter.submitList(colorPickerAdapter.colorList)
    }

    private fun observe() {
        viewModel.isSuccess.observe(this, EventObserver {
            setResult(RESULT_OK)
            finish()
        })
        viewModel.isFailure.observe(this, EventObserver {
            setResult(RESULT_CANCELED)
            val snackBar = Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("확인") {
                snackBar.dismiss()
            }.show()
        })
    }

    private fun listener() = with(binding) {
        with(viewModel) {
            submitBtnText.value = intent.getStringExtra("btnText") ?: "추가"
            title.value = intent.getStringExtra("oldTitle") ?: ""
            oldTitle.value = intent.getStringExtra("oldTitle") ?: ""
            with(colorPickerAdapter) {
                color.value = intent.getStringExtra("oldColor") ?: colorList[0]
                if (intent.getStringExtra("oldColor") != null) {
                    var i = 0
                    colorList.forEach {
                        if (it == intent.getStringExtra("oldColor")) {
                            checkedItem = i
                            return@forEach
                        } else if (
                            it != intent.getStringExtra("oldColor") &&
                            colorList.lastIndex == i
                        ) {
                            checkedItem = -1
                        }
                        i++
                    }
                }
            }
        }

        toolbarAddSubject.setNavigationOnClickListener {
            finish()
        }

        btnAddSubject.setOnClickListener {
            with(viewModel) {
                if (intent.getStringExtra("btnText") != null) {
                    modifySubject()
                } else {
                    addSubject()
                }
            }
        }
    }
}