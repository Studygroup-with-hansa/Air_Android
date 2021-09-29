package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.hansarang.android.air.databinding.FragmentStatsBinding
import com.hansarang.android.air.ui.adapter.ChartLegendListAdapter
import com.hansarang.android.air.ui.adapter.WeekdayDatePickerAdapter
import com.hansarang.android.air.ui.viewmodel.fragment.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.view.MotionEvent

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
        val pieColorList = ColorTemplate.createColors(ColorTemplate.JOYFUL_COLORS)
        weekDayDatePickerAdapter = WeekdayDatePickerAdapter()
        weekDayDatePickerAdapter.stats.observe(viewLifecycleOwner) {
            binding.stats = it

            val pieEntry =
                arrayListOf(
                    PieEntry(10f, "국어"),
                    PieEntry(10f, "수학"),
                    PieEntry(10f, "사회"),
                    PieEntry(10f, "과학"),
                    PieEntry(10f, "영어"),
                    PieEntry(10f, "웹프"),
                    PieEntry(10f, "프실"),
                    PieEntry(10f, "파이썬"),
                    PieEntry(10f, "C++"),
                    PieEntry(10f, "자바"),
                )

            chartLegendListAdapter = ChartLegendListAdapter(pieColorList)
            chartLegendListAdapter.submitList(pieEntry)

            with(binding.rvChartLegendStats) {
                adapter = chartLegendListAdapter
            }

            val pieDataSet = PieDataSet(pieEntry, "")
                .apply {
                    sliceSpace = 3f
                    selectionShift = 5f
                    colors = pieColorList
                }
            val pieData = PieData(pieDataSet)

            with(binding.chartStudyTimeStats) {
                data = pieData.apply { setDrawValues(false) }
                legend.isEnabled = false
                description.text = ""
                holeRadius = 60f
                dragDecelerationFrictionCoef = 0.95f
                setDrawEntryLabels(false)
                animateY(500, Easing.EaseInOutQuart)

                setOnTouchListener { view, motionEvent ->
                    if (motionEvent.action == MotionEvent.ACTION_UP) {
                        view.performClick()
                    }
                    return@setOnTouchListener true
                }
            }
        }

        viewModel.stats.observe(viewLifecycleOwner) { weekDayDatePickerAdapter.submitList(it) }
    }

}