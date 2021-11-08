package com.hansarang.android.air.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hansarang.android.air.databinding.DialogFragmentAlertBinding

class DialogAlertFragment : DialogFragment() {

    interface OnNegativeButtonClickListener {
        fun onClick()
    }

    interface OnPositiveButtonClickListener {
        fun onClick()
    }

    private var onNegativeButtonClickListener: OnNegativeButtonClickListener =
        object : OnNegativeButtonClickListener {
            override fun onClick() {
                dismiss()
            }
        }
    private var onPositiveButtonClickListener: OnPositiveButtonClickListener =
        object : OnPositiveButtonClickListener {
            override fun onClick() {}
        }

    private lateinit var binding: DialogFragmentAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAlertBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            with(binding) {
                title = it.getString("title")
                content = it.getString("content", "Alert")
                negativeBtnText = it.getString("negativeBtnText", "취소")
                positiveBtnText = it.getString("positiveBtnText", "확인")
                btnNegativeAlert.setOnClickListener {
                    onNegativeButtonClickListener.onClick()
                }
                btnPositiveAlert.setOnClickListener {
                    onPositiveButtonClickListener.onClick()
                }
            }
        }

    }

    fun setOnNegativeButtonClickListener(onClick: () -> Unit) {
        onNegativeButtonClickListener = object : OnNegativeButtonClickListener {
            override fun onClick() {
                onClick.invoke()
                dismiss()
            }
        }
    }

    fun setOnPositiveButtonClickListener(onClick: () -> Unit) {
        onPositiveButtonClickListener = object : OnPositiveButtonClickListener {
            override fun onClick() {
                onClick.invoke()
                dismiss()
            }
        }
    }


    companion object {
        fun newInstance(title: String, content: String) =
            DialogAlertFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("content", content)
                }
            }

        fun newInstance(
            title: String,
            content: String,
            positiveBtnText: String,
            negativeBtnText: String
        ) =
            DialogAlertFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("content", content)
                    putString("positiveBtnText", positiveBtnText)
                    putString("negativeBtnText", negativeBtnText)
                }
            }
    }
}