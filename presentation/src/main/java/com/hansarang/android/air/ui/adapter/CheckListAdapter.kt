package com.hansarang.android.air.ui.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemCheckListBinding

class CheckListAdapter: ListAdapter<String, CheckListAdapter.ViewHolder>(diffUtil) {

    lateinit var onClickDeleteListener: OnClickDeleteListener
    interface OnClickDeleteListener {
        fun onClick(value: String)
    }
    fun setOnClickDeleteListener(listener: (String) -> Unit) {
        onClickDeleteListener = object : OnClickDeleteListener {
            override fun onClick(value: String) {
                listener(value)
            }
        }
    }

    lateinit var onClickModifyListener: OnClickModifyListener
    interface OnClickModifyListener {
        fun onClick(beforeValue: String, afterValue: String)
    }
    fun setOnClickModifyListener(listener: (String, String) -> Unit) {
        onClickModifyListener = object : OnClickModifyListener {
            override fun onClick(beforeValue: String, afterValue: String) {
                listener(beforeValue, afterValue)
            }

        }
    }

    inner class ViewHolder(private val binding: ItemCheckListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(value: String) = with(binding) {
            etTodoCheckList.setText(value)
            btnDeleteTodoCheckList.setOnClickListener {
                onClickDeleteListener.onClick(value)
            }
            etTodoCheckList.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    onClickModifyListener.onClick(value, etTodoCheckList.text.toString())
                }
                return@setOnKeyListener true
            }

            chkTodoCheckList.setOnCheckedChangeListener { view, isChecked ->
                with(etTodoCheckList) {
                    paintFlags =
                        if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG
                        else 0

                    setTextColor(ContextCompat.getColor(view.context,
                        if (isChecked) R.color.grey_disabled
                        else R.color.black
                    ))

                    isFocusable = !isChecked
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCheckListBinding.inflate(inflater)
        binding.root.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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