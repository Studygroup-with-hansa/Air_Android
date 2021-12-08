package com.hansarang.android.air.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.hansarang.android.air.databinding.FragmentHomeBinding
import com.hansarang.android.air.ui.activity.AddSubjectActivity
import com.hansarang.android.air.ui.adapter.TimerSubjectListAdapter
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.dialog.SetGoalDialogFragment
import com.hansarang.android.air.ui.viewmodel.fragment.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by activityViewModels()
    private lateinit var timerSubjectListAdapter: TimerSubjectListAdapter
    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            viewModel.getSubject()
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.isFirstLoad) {
            viewModel.getSubject()
        }
    }

    override fun observerViewModel() {

        timerSubjectListAdapter = TimerSubjectListAdapter(viewModel, activityResultLauncher)
        binding.rvSubjectHome.adapter = timerSubjectListAdapter

        viewModel.setTargetTimeButtonClick.observe(viewLifecycleOwner) {
            val setGoalDialogFragment = SetGoalDialogFragment.newInstance()
            setGoalDialogFragment.show(parentFragmentManager, "setGoalDialogFragment")
            setGoalDialogFragment.setOnDismissDialogListener {
                viewModel.getSubject()
            }
        }

        viewModel.addSubjectButtonClick.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), AddSubjectActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        viewModel.subjectList.observe(viewLifecycleOwner) {
            timerSubjectListAdapter.submitList(it.toMutableList())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.sflToolbarHome.startShimmer()
                binding.sflRvSubjectHome.startShimmer()
            }
            else {
                binding.sflToolbarHome.stopShimmer()
                binding.sflRvSubjectHome.stopShimmer()
                binding.srlHome.isRefreshing = false
            }
        }
        viewModel.setTargetTimeButtonClick.observe(viewLifecycleOwner) {
            val setGoalDialogFragment = SetGoalDialogFragment.newInstance()
            setGoalDialogFragment.show(parentFragmentManager, "setGoalDialogFragment")
            setGoalDialogFragment.setOnDismissDialogListener {
                viewModel.getSubject()
            }
        }
    }

}