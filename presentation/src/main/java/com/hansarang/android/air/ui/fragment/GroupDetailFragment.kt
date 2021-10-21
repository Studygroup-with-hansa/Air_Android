package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentGroupDetailBinding
import com.hansarang.android.air.ui.adapter.GroupRankAdapter
import com.hansarang.android.air.ui.viewmodel.fragment.GroupDetailViewModel

class GroupDetailFragment : Fragment() {

    private lateinit var binding: FragmentGroupDetailBinding
    private val viewModel: GroupDetailViewModel by viewModels()
    private lateinit var groupRankAdapter: GroupRankAdapter
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRank()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()
    }

    private fun observe() = with(viewModel) {
        groupRankList.observe(viewLifecycleOwner) {
            groupRankAdapter.submitList(it)
        }
    }

    private fun init() = with(binding) {
        groupRankAdapter = GroupRankAdapter()
        rvRankGroupDetail.adapter = groupRankAdapter
        toolbarGroupDetail.setNavigationOnClickListener {
            navController.navigateUp()
        }
    }

}