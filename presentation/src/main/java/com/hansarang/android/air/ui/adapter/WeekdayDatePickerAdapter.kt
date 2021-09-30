package com.hansarang.android.air.ui.adapter

import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemWeekdayDatePickerBinding
import com.hansarang.android.domain.entity.dto.Stats
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeekdayDatePickerAdapter: ListAdapter<Stats, WeekdayDatePickerAdapter.ViewHolder>(diffUtil) {

    private val _stats = MutableLiveData<Stats>()
    val stats: LiveData<Stats> = _stats

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
                viewBackgroundColor.alpha = achievement
                with(cbDateWeekdayDatePicker) {
                    text = DateFormat.format("dd", sdf.parse(date))
                    isChecked = checkedItem == adapterPosition
                    setTextColor(if (achievement > 0.5f) {
                        ContextCompat.getColor(context, R.color.white)
                    } else {
                        ContextCompat.getColor(context, R.color.black)
                    })
                    setOnClickListener {
                        _stats.value = stats
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
        if (checkedItem == position) _stats.value = getItem(position)
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