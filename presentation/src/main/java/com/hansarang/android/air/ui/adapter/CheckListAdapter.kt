package com.hansarang.android.air.ui.adapter

import android.graphics.Paint
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemCheckListBinding
import com.hansarang.android.air.ui.viewmodel.adapter.TodoListAdapterViewModel
import com.hansarang.android.domain.entity.dto.CheckListItem
import java.util.*

class CheckListAdapter(
    private val viewModel: TodoListAdapterViewModel
): RecyclerView.Adapter<CheckListAdapter.ViewHolder>() {

    private val list = ArrayList<CheckListItem>()

    fun submitList(list: List<CheckListItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): CheckListItem {
        return list[position]
    }

    fun getList(): MutableList<CheckListItem> {
        return list
    }

    fun addItem(checkListItem: CheckListItem): MutableList<CheckListItem> {
        val curList = list.toMutableList()
        curList.add(checkListItem)
        submitList(curList)
        return curList
    }

    fun removeItem(checkListItem: CheckListItem): MutableList<CheckListItem> {
        val curList = list.toMutableList()
        curList.remove(checkListItem)
        submitList(curList)
        return curList
    }

    lateinit var onClickDeleteListener: OnClickDeleteListener
    interface OnClickDeleteListener {
        fun onClick(checkListItem: CheckListItem)
    }
    fun setOnClickDeleteListener(listener: (CheckListItem) -> Unit) {
        onClickDeleteListener = object : OnClickDeleteListener {
            override fun onClick(checkListItem: CheckListItem) {
                listener(checkListItem)
            }
        }
    }

    lateinit var onClickModifyListener: OnClickModifyListener
    interface OnClickModifyListener {
        fun onClick(checkListItem: CheckListItem, newTitle: String)
    }
    fun setOnClickModifyListener(listener: (CheckListItem, String) -> Unit) {
        onClickModifyListener = object : OnClickModifyListener {
            override fun onClick(checkListItem: CheckListItem, newTitle: String) {
                listener(checkListItem, newTitle)
            }

        }
    }

    lateinit var onCheckedChangeListener: OnCheckedChangeListener
    interface OnCheckedChangeListener {
        fun onClick(checkListItem: CheckListItem)
    }
    fun setOnCheckedChangeListener(listener: (CheckListItem) -> Unit) {
        onCheckedChangeListener = object : OnCheckedChangeListener {
            override fun onClick(checkListItem: CheckListItem) {
                listener(checkListItem)
            }
        }
    }

    inner class ViewHolder(private val binding: ItemCheckListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(checkListItem: CheckListItem) = with(binding) {

            setChecked(checkListItem.isitDone)
            etTodoCheckList.setText(checkListItem.todo)

            btnDeleteTodoCheckList.setOnClickListener {
                onClickDeleteListener.onClick(checkListItem)
            }
            etTodoCheckList.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    onClickModifyListener.onClick(checkListItem, etTodoCheckList.text.toString())
                }
                return@setOnKeyListener true
            }

            chkTodoCheckList.setOnCheckedChangeListener { view, isChecked ->
                setChecked(isChecked)
                onCheckedChangeListener.onClick(checkListItem)
            }
        }

        private fun setChecked(isChecked: Boolean) = with(binding) {
            chkTodoCheckList.isChecked = isChecked
            etTodoCheckList.paintFlags = if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
            etTodoCheckList.setTextColor(ContextCompat.getColor(etTodoCheckList.context,
                if (isChecked) R.color.bnv_disabled
                else R.color.black
            ))
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

    override fun getItemCount(): Int {
        return list.size
    }
}