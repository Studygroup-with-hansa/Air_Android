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
            binding.stats = it
            val achievement = with(it) { totalStudyTime.toFloat() / goal.toFloat() }

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

            chartLegendListAdapter = ChartLegendListAdapter(pieColorList)
            chartLegendListAdapter.submitList(pieEntryList)
            binding.rvChartLegendStats.adapter = chartLegendListAdapter

            with(binding.chartStudyTimeStats) {
                data = pieData.apply { setDrawValues(false) }
                legend.isEnabled = false
                description.text = ""
                holeRadius = 60f
                dragDecelerationFrictionCoef = 0.95f
                setDrawEntryLabels(false)
                animateY(500, Easing.EaseInOutQuart)
            }

            with(binding.tvGoalAchievementRateStats) {
                text = String.format(
                    resources.getString(R.string.goal_achievement_rate_stats),
                    (achievement * 100).toInt()
                )
                setTextColor(if (achievement > 0.5f) {
                    ContextCompat.getColor(context, R.color.white)
                } else {
                    ContextCompat.getColor(context, R.color.black)
                })
            }

            binding.viewGoalAchievementRateBackgroundStats.alpha = achievement
        }

        viewModel.stats.observe(viewLifecycleOwner) { weekDayDatePickerAdapter.submitList(it) }
    }

}