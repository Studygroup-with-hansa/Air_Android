package com.hansarang.android.air.ui.adapter

import android.graphics.Paint
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemCheckListBinding
import com.hansarang.android.air.ui.viewmodel.adapter.TodoListAdapterViewModel
import com.hansarang.android.air.ui.viewmodel.fragment.TodoViewModel
import com.hansarang.android.domain.entity.dto.CheckListItem
import java.text.SimpleDateFormat
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

    inner class ViewHolder(private val binding: ItemCheckListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(checkListItem: CheckListItem) = with(binding) {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val calendar = Calendar.getInstance().time
            val isToday = sdf.format(Date(viewModel.date)) == sdf.format(calendar)

            binding.isToday = isToday

            setChecked(checkListItem.isitDone)
            etTodoCheckList.setText(checkListItem.todo)
            val toast = Toast.makeText(itemView.context, "이전 날짜의 할 일은 수정할 수 없습니다.", Toast.LENGTH_SHORT)
            
            btnDeleteTodoCheckList.setOnClickListener {
                if (isToday) {
                    onClickDeleteListener.onClick(checkListItem)
                } else {
                    toast.show()
                }
            }
            etTodoCheckList.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    onClickModifyListener.onClick(checkListItem, etTodoCheckList.text.toString())
                }
                return@setOnKeyListener true
            }

            chkTodoCheckList.setOnCheckedChangeListener { view, isChecked ->
                setChecked(isChecked)
                viewModel.putStatusChangeCheckList(checkListItem.pk)
            }
        }

        private fun setChecked(isChecked: Boolean) = with(binding) {
            chkTodoCheckList.isChecked = isChecked
            etTodoCheckList.isActivated = !isChecked
            etTodoCheckList.paintFlags = if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
            etTodoCheckList.setTextColor(ContextCompat.getColor(etTodoCheckList.context,
                if (isChecked) R.color.grey_disabled
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