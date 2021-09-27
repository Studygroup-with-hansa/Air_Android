package com.hansarang.android.air.ui.adapter

import android.text.format.DateFormat
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

    interface OnClickCheckBox {
        fun onClick(stats: Stats)
    }

    private lateinit var onClickCheckBox: OnClickCheckBox

    class ViewHolder(private val binding: ItemWeekdayDatePickerBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(stats: Stats, position: Int, onClickCheckBox: OnClickCheckBox) {
            val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.KOREA)
            val date = stats.date
            with(binding) {
                with(tvDayWeekdayDatePicker) {
                    text = dayOfWeek.format(sdf.parse(date)?:"").replace("요일", "")
                    frameLayoutDateBackgroundWeekdayDatePicker.background = ContextCompat.getDrawable(context, R.color.white)
//                        if (stats.totalStudyTime != 0) {
//                            ContextCompat.getDrawable(context, R.color.main_sub)
//                        } else {
//                            ContextCompat.getDrawable(context, R.color.white)
//                        }
                }
                with(cbDateWeekdayDatePicker) {
                    text = DateFormat.format("dd", sdf.parse(date))
                    isChecked = checkedItem[position]
                    setOnClickListener {
                        for (k in checkedItem.indices) {
                            checkedItem[k] = (k == position)
                        }
                        isChecked = checkedItem[position]
                        onClickCheckBox.onClick(stats)
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
        if (checkedItem[position]) _stats.value = getItem(position)

        onClickCheckBox = object : OnClickCheckBox {
            override fun onClick(stats: Stats) {
                _stats.value = stats
                notifyDataSetChanged()
            }
        }

        holder.bind(getItem(position), position, onClickCheckBox)
    }

    companion object {

        private val checkedItem = ArrayList<Boolean>(7)
        init {
            for (i in 0..6) {
                checkedItem.add(i == 6)
            }
        }

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