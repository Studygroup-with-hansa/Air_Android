package com.hansarang.android.air.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.ItemColorPickerBinding

class ColorPickerAdapter: ListAdapter<ColorPickerAdapter.ColorValue, ColorPickerAdapter.ViewHolder>(diffUtil) {

    val colorList: ArrayList<ColorValue> = arrayListOf(
        ColorValue("#ED6A5E"),
        ColorValue("#F6C343"),
        ColorValue("#79D16E"),
        ColorValue("#97BAFF"),
        ColorValue("#8886FF"),
    )

    var checkedItem = 0

    data class ColorValue(val color: String)

    inner class ViewHolder(private val binding: ItemColorPickerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(colorValue: ColorValue) = with(binding) {
            color = Color.parseColor(colorValue.color)
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
        holder.bind(getItem(position))
    }

    companion object {

        val diffUtil = object : DiffUtil.ItemCallback<ColorValue>() {
            override fun areItemsTheSame(oldItem: ColorValue, newItem: ColorValue): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ColorValue, newItem: ColorValue): Boolean {
                return oldItem == newItem
            }

        }
    }
}