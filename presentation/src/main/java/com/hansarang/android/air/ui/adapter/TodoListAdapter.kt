package com.hansarang.android.air.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ItemTodoBinding
import com.hansarang.android.air.ui.bind.collapse
import com.hansarang.android.air.ui.bind.expand
import com.hansarang.android.air.ui.bind.setToggleEnabled
import com.hansarang.android.domain.entity.dto.Todo

class TodoListAdapter: ListAdapter<Todo, TodoListAdapter.ViewHolder>(diffUtil) {

    class ViewHolder(
        private val binding: ItemTodoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: Todo) = with(binding) {
            binding.todo = todo
            btnExpendTodo.setOnClickListener {
                todo.isExpended = !todo.isExpended
                ivExpendTodo.setToggleEnabled(todo.isExpended)
                binding.btnExpendTodo.isSelected = if (todo.isExpended) {
                    linearLayoutCheckListTodo.expand()
                    true
                } else {
                    linearLayoutCheckListTodo.collapse()
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(inflater)
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
        private val diffUtil = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }
}