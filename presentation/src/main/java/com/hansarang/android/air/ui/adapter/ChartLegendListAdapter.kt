package com.hansarang.android.air.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.PieEntry
import com.hansarang.android.air.databinding.ItemChartLegendBinding

class ChartLegendListAdapter(private var colorList: List<Int>): RecyclerView.Adapter<ChartLegendListAdapter.ViewHolder>() {
    private var colorPos = 0
    private val pieEntryList = ArrayList<PieEntry>()

    fun submitList(list: ArrayList<PieEntry>) {
        this.pieEntryList.clear()
        this.pieEntryList.addAll(list)
    }

    inner class ViewHolder(private val binding: ItemChartLegendBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pieEntry: PieEntry) = with(binding) {
            tvElementChartLegend.text = pieEntry.label
            viewFormChartLegend.setBackgroundColor(colorList[colorPos])
            colorPos++
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemChartLegendBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (colorPos > colorList.lastIndex) {
            colorPos = 0
        }
        holder.bind(pieEntryList[position])
    }

    override fun getItemCount(): Int {
        return pieEntryList.size
    }
}