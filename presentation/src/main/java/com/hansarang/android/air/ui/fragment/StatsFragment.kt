package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.hansarang.android.air.databinding.FragmentStatsBinding
import com.hansarang.android.air.ui.adapter.WeekdayDatePickerAdapter
import com.hansarang.android.air.ui.viewmodel.fragment.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var adapter: WeekdayDatePickerAdapter
    private val viewModel: StatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.getStats()
        binding.rvWeekdayDatePicker.adapter = adapter
    }

    private fun init() {
        adapter = WeekdayDatePickerAdapter()
        adapter.stats.observe(viewLifecycleOwner) {
            binding.stats = it
        }
        viewModel.stats.observe(viewLifecycleOwner) { adapter.submitList(it) }

        val dataSet = PieDataSet(arrayListOf(PieEntry(100f, "국어")), "")
        with(binding.chartStudyTimeStats) {
            data = PieData(dataSet)
            description.text = ""
        }


    }

}