package com.hansarang.android.air.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.ItemColorPickerBinding
import com.hansarang.android.air.ui.viewmodel.activity.AddSubjectViewModel

class ColorPickerAdapter(private val viewModel: AddSubjectViewModel): ListAdapter<String, ColorPickerAdapter.ViewHolder>(diffUtil) {

    val colorList: ArrayList<String> = arrayListOf(
        "#ED6A5E",
        "#F6C343",
        "#79D16E",
        "#97BAFF",
        "#8886FF",
    )

    var checkedItem = -1
        set(value) {
            notifyItemChanged(checkedItem)
            field = value
            if (value > -1) {
                viewModel.color.value = colorList[value]
            }
        }

    inner class ViewHolder(private val binding: ItemColorPickerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(colorValue: String) = with(binding) {
            color = Color.parseColor(colorValue)
            chkColorPicker.isChecked = checkedItem == adapterPosition
            chkColorPicker.setOnClickListener {
                if (checkedItem != adapterPosition) {
                    notifyItemChanged(checkedItem)
                    checkedItem = adapterPosition
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemColorPickerBinding.inflate(inflater))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position > -1)
            holder.bind(getItem(position))
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

        }
    }
}