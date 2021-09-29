package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
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

            val pieEntry = arrayListOf(PieEntry(40f, "국어"), PieEntry(60f, "수학"))

            val pieDataSet = PieDataSet(pieEntry, "")
                .apply {
                    sliceSpace = 3f
                    selectionShift = 5f
                    colors = ColorTemplate.createColors(ColorTemplate.JOYFUL_COLORS)
                }
            val pieData = PieData(pieDataSet)

            with(binding.chartStudyTimeStats) {
                data = pieData.apply { setDrawValues(false) }
                legend.isEnabled = false
                description.text = ""
                holeRadius = 60f
                dragDecelerationFrictionCoef = 0.95f
                setExtraOffsets(20f, 20f, 20f, 20f)
                setDrawEntryLabels(false)
                animateY(500, Easing.EaseInOutQuart)
            }
        }

        viewModel.stats.observe(viewLifecycleOwner) { adapter.submitList(it) }


    }

}