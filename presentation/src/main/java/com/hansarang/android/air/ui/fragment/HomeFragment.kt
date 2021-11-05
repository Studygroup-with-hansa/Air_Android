package com.hansarang.android.air.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hansarang.android.air.databinding.FragmentHomeBinding
import com.hansarang.android.air.ui.activity.AddSubjectActivity
import com.hansarang.android.air.ui.adapter.TimerSubjectListAdapter
import com.hansarang.android.air.ui.dialog.SetGoalDialogFragment
import com.hansarang.android.air.ui.viewmodel.fragment.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import androidx.recyclerview.widget.SimpleItemAnimator


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var timerSubjectListAdapter: TimerSubjectListAdapter
    private var activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            viewModel.getSubjectList()
        }
    }

    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.isFirstLoad) {
            viewModel.getSubjectList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        listener()
        observe()
    }

    private fun listener() = with(binding) {
        fabAddSubjectHome.setOnClickListener {
            val intent = Intent(requireContext(), AddSubjectActivity::class.java)
            activityResultLauncher.launch(intent)
        }

        tvTargetTimeHome.setOnClickListener {
            val setGoalDialogFragment = SetGoalDialogFragment.newInstance()
            setGoalDialogFragment.show(parentFragmentManager, "setGoalDialogFragment")
        }

        srlHome.setOnRefreshListener {
            viewModel.getSubjectList()
        }
    }

    private fun observe() = with(viewModel) {
        subjectList.observe(viewLifecycleOwner) {
            timerSubjectListAdapter.submitList(it.toMutableList())
        }
        isLoading.observe(viewLifecycleOwner) {
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
    }

    private fun init() = with(binding) {
        timerSubjectListAdapter = TimerSubjectListAdapter(viewModel, activityResultLauncher)
        rvSubjectHome.adapter = timerSubjectListAdapter
        val animator = rvSubjectHome.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
        tvDateHome.text = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Date(System.currentTimeMillis()))
    }

}