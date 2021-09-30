package com.hansarang.android.air.ui.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemWeekdayDatePickerBinding
import com.hansarang.android.air.ui.viewmodel.adapter.WeekdayDatePickerAdapterViewModel
import com.hansarang.android.domain.entity.dto.Stats
import java.text.SimpleDateFormat
import java.util.*

class WeekdayDatePickerAdapter(
    private val viewModel: WeekdayDatePickerAdapterViewModel
): ListAdapter<Stats, WeekdayDatePickerAdapter.ViewHolder>(diffUtil) {

    private var checkedItem = 6

    inner class ViewHolder(private val binding: ItemWeekdayDatePickerBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(stats: Stats) {
            val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.KOREA)
            val date = stats.date
            val achievement = with(stats) {
                totalStudyTime.toFloat() / goal.toFloat()
            }

            with(binding) {
                tvDayWeekdayDatePicker.text = dayOfWeek.format(sdf.parse(date)?:"").replace("요일", "")
                with(cbDateWeekdayDatePicker) {
                    viewBackgroundColor.alpha =
                        if (stats.totalStudyTime > 0) {
                            setTextColor(ContextCompat.getColor(context, R.color.white))
                            if (achievement > 0.3f) achievement else 0.3f
                        } else {
                            setTextColor(ContextCompat.getColor(context, R.color.black))
                            0f
                        }

                    text = DateFormat.format("dd", sdf.parse(date))
                    isChecked = checkedItem == adapterPosition
                    setOnClickListener {
                        viewModel.stats.value = stats
                        if (checkedItem != adapterPosition) {
                            notifyItemChanged(checkedItem)
                            checkedItem = adapterPosition
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemWeekdayDatePickerBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (checkedItem == position) viewModel.stats.value = getItem(position)
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Stats>() {
            override fun areItemsTheSame(oldItem: Stats, newItem: Stats): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Stats, newItem: Stats): Boolean {
                return oldItem == newItem
            }
        }
    }
}