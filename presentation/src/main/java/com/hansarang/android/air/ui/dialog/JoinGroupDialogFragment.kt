package com.hansarang.android.air.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.hansarang.android.air.databinding.DialogFragmentJoinGroupBinding
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.dialog.JoinGroupDialogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinGroupDialogFragment : DialogFragment() {

    private lateinit var onDismissDialogListener: OnDismissDialogListener
    interface OnDismissDialogListener {
        fun onClick(message: String)
    }

    private lateinit var binding: DialogFragmentJoinGroupBinding
    private val viewModel: JoinGroupDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentJoinGroupBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    private fun observe() = with(viewModel) {
        isDismissed.observe(viewLifecycleOwner, EventObserver {
            onDismissDialogListener.onClick("")
            dismiss()
        })
        isFailure.observe(viewLifecycleOwner, EventObserver {
            onDismissDialogListener.onClick(it)
            dismiss()
        })
    }

    fun setOnDismissDialogListener(listener: (String) -> Unit) {
        onDismissDialogListener = object : OnDismissDialogListener {
            override fun onClick(message: String) {
                listener(message)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            JoinGroupDialogFragment()
    }
}