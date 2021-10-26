package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hansarang.android.air.databinding.FragmentHomeBinding
import com.hansarang.android.air.ui.activity.AddSubjectActivity
import com.hansarang.android.air.ui.adapter.TimerSubjectListAdapter
import com.hansarang.android.air.ui.dialog.SetGoalDialogFragment
import com.hansarang.android.air.ui.viewmodel.fragment.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var timerSubjectListAdapter: TimerSubjectListAdapter

    private val viewModel: HomeViewModel by viewModels()

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
        viewModel.getSubjectList()
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
            startActivity(intent)
        }

        tvTargetTimeHome.setOnClickListener {
            val setGoalDialogFragment = SetGoalDialogFragment.newInstance()
            setGoalDialogFragment.show(parentFragmentManager, "setGoalDialogFragment")
        }
    }

    private fun observe() = with(viewModel) {
        subjectList.observe(viewLifecycleOwner) {
            timerSubjectListAdapter.submitList(it.toMutableList())
        }
    }

    private fun init() = with(binding) {
        timerSubjectListAdapter = TimerSubjectListAdapter(viewModel)
        rvSubjectHome.adapter = timerSubjectListAdapter
        toolbarHome.setNavigationOnClickListener {
            drawerLayoutHome.open()
        }
        tvDateHome.text = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA).format(Date(System.currentTimeMillis()))
    }

}