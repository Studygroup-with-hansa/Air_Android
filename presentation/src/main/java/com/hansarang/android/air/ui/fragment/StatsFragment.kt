package com.hansarang.android.air.ui.fragment

import android.graphics.Color
import androidx.fragment.app.activityViewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.hansarang.android.air.databinding.FragmentStatsBinding
import com.hansarang.android.air.ui.adapter.ChartLegendListAdapter
import com.hansarang.android.air.ui.adapter.WeekdayDatePickerAdapter
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.viewmodel.adapter.WeekdayDatePickerAdapterViewModel
import com.hansarang.android.air.ui.viewmodel.fragment.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : BaseFragment<FragmentStatsBinding, StatsViewModel>() {

    private lateinit var weekDayDatePickerAdapter: WeekdayDatePickerAdapter
    private lateinit var chartLegendListAdapter: ChartLegendListAdapter
    override val viewModel: StatsViewModel by activityViewModels()
    private val weekdayDatePickerAdapterViewModel: WeekdayDatePickerAdapterViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        if (viewModel.isFirstLoad)
            viewModel.getStats()
    }

    override fun observerViewModel() {
        binding.weekdayDatePickerAdapterVm = weekdayDatePickerAdapterViewModel
        weekDayDatePickerAdapter = WeekdayDatePickerAdapter(weekdayDatePickerAdapterViewModel)
        binding.rvWeekdayDatePickerStats.adapter = weekDayDatePickerAdapter

        weekdayDatePickerAdapterViewModel.stats.observe(viewLifecycleOwner) {
            with(it) {
                if (goal >= 0) {
                    binding.achievement = totalStudyTime.toFloat() / goal.toFloat()
                    val pieColorList = ArrayList<Int>()
                    val pieEntryList = ArrayList<PieEntry>()
                    subject.forEach { subject ->
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
                        setHoleColor(Color.TRANSPARENT)
                        setDrawEntryLabels(false)
                        animateY(500, Easing.EaseInOutQuart)
                    }

                    chartLegendListAdapter = ChartLegendListAdapter(pieColorList)
                    chartLegendListAdapter.submitList(pieEntryList)
                    binding.rvChartLegendStats.adapter = chartLegendListAdapter
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (!it) binding.srlStats.isRefreshing = false
        }

        viewModel.statList.observe(viewLifecycleOwner) { weekDayDatePickerAdapter.submitList(it) }
    }

}