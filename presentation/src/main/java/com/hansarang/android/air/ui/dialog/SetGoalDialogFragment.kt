package com.hansarang.android.air.ui.dialog

import android.os.Bundle
import android.text.Selection
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.hansarang.android.air.databinding.DialogFragmentSetGoalBinding
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.dialog.SetGoalDialogViewModel

class SetGoalDialogFragment : DialogFragment() {

    private val viewModel: SetGoalDialogViewModel by viewModels()
    private lateinit var binding: DialogFragmentSetGoalBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentSetGoalBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModelStore.clear()
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    private fun observe() = with(viewModel) {
        hour.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Selection.setSelection(
                    binding.etHourSetGoal.text,
                    binding.etHourSetGoal.length()
                )
            }
        }
        minute.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Selection.setSelection(
                    binding.etMinuteSetGoal.text,
                    binding.etMinuteSetGoal.length()
                )
            }
        }
        second.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Selection.setSelection(
                    binding.etSecondSetGoal.text,
                    binding.etSecondSetGoal.length()
                )
            }
        }
        isDismissed.observe(viewLifecycleOwner, EventObserver {
            dismiss()
        })
        isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SetGoalDialogFragment()
    }
}