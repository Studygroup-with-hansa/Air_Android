package com.hansarang.android.air.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentStatsBinding
import com.hansarang.android.air.ui.adapter.ChartLegendListAdapter
import com.hansarang.android.air.ui.adapter.WeekdayDatePickerAdapter
import com.hansarang.android.air.ui.viewmodel.fragment.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private lateinit var weekDayDatePickerAdapter: WeekdayDatePickerAdapter
    private lateinit var chartLegendListAdapter: ChartLegendListAdapter
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
        binding.rvWeekdayDatePickerStats.adapter = weekDayDatePickerAdapter
    }

    private fun init() {
        weekDayDatePickerAdapter = WeekdayDatePickerAdapter()
        weekDayDatePickerAdapter.stats.observe(viewLifecycleOwner) {
            if (it.totalStudyTime != 0) {
                binding.tvNoDataStats.visibility = View.GONE
                binding.linearLayoutDataGraphStats.visibility = View.VISIBLE

                binding.stats = it
                binding.achievement = with(it) { totalStudyTime.toFloat() / goal.toFloat() }

                val pieColorList = ArrayList<Int>()
                val pieEntryList = ArrayList<PieEntry>()
                it.subject.forEach { subject ->
                    pieColorList.add(Color.parseColor(subject.color))
                    pieEntryList.add(PieEntry(subject.time.toFloat(), subject.title))
                }
                val pieDataSet = PieDataSet(pieEntryList, "")
                    .apply {
                        sliceSpace = 3f
                        selectionShift = 5f
                        colors = pieColorList
                    }
                val pieData = PieData(pieDataSet)

                with(binding.chartStudyTimeStats) {
                    rotationAngle = 270f
                    data = pieData.apply { setDrawValues(false) }
                    legend.isEnabled = false
                    description.text = ""
                    holeRadius = 60f
                    dragDecelerationFrictionCoef = 0.95f
                    setDrawEntryLabels(false)
                    animateY(500, Easing.EaseInOutQuart)
                }

                chartLegendListAdapter = ChartLegendListAdapter(pieColorList)
                chartLegendListAdapter.submitList(pieEntryList)
                binding.rvChartLegendStats.adapter = chartLegendListAdapter
            } else {
                binding.tvNoDataStats.visibility = View.VISIBLE
                binding.linearLayoutDataGraphStats.visibility = View.GONE
            }
        }

        viewModel.stats.observe(viewLifecycleOwner) { weekDayDatePickerAdapter.submitList(it) }
    }

}