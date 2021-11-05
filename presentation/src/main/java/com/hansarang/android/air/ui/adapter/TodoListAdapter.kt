package com.hansarang.android.air.ui.adapter

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.ItemTodoBinding
import com.hansarang.android.air.ui.bind.*
import com.hansarang.android.air.ui.decorator.ItemDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.viewmodel.fragment.TodoViewModel
import com.hansarang.android.domain.entity.dto.Todo
import java.text.SimpleDateFormat
import java.util.*

class TodoListAdapter(private val viewModel: TodoViewModel): ListAdapter<Todo, TodoListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(
        private val binding: ItemTodoBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo, position: Int) = with(binding) {

            with(todo) {
                var todoDoneCount = 0
                todoList.forEach { if (it.isitDone) todoDoneCount++ }
                val todoListCount = todoList.size
                tvPercentsTodo.text = String.format("%d%% 달성", (todoDoneCount.toFloat() / todoListCount.toFloat()).toInt() * 100)
            }

            ivExpendTodo.setToggleEnabled(todo.isExpended)
            btnExpendTodo.isSelected = todo.isExpended
            if (todo.isExpended) {
                nestedScrollViewCheckListTodo.setExpend()
            } else {
                nestedScrollViewCheckListTodo.setCollapse()
            }

            val checkListAdapter = CheckListAdapter(viewModel)

            rvCheckListTodo.run {
                adapter = checkListAdapter
                checkListAdapter.submitList(todo.todoList.toMutableList())
                if (itemDecorationCount == 0) addItemDecoration(ItemDividerDecorator(5.dp))
                linearLayoutHorizontalCheckListTodo.setPadding(0,0,0,
                    if (todo.todoList.isEmpty()) 10.dp else 0
                )
            }

            binding.todo = todo
            btnExpendTodo.setOnClickListener {
                todo.isExpended = !todo.isExpended
                ivExpendTodo.setToggleEnabled(todo.isExpended)
                btnExpendTodo.isSelected = todo.isExpended
                if (todo.isExpended) {
                    nestedScrollViewCheckListTodo.expandAnimation()
                } else {
                    nestedScrollViewCheckListTodo.collapseAnimation()
                }
            }

            etAddCheckListTodo.setOnKeyListener { view, keyCode, keyEvent ->
                val editText = view as EditText
                if (keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.action == KeyEvent.ACTION_DOWN) {
                    Log.d("Todo", "bind: ${editText.text}")
                    if (etAddCheckListTodo.text.isNotEmpty()) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                        val date = sdf.format(Date(System.currentTimeMillis()))
                        viewModel.postCheckList(todo.subject, date, etAddCheckListTodo.text.toString())
                        etAddCheckListTodo.setText("")
                        notifyItemChanged(position)
                    }
                }
                return@setOnKeyListener true
            }

            checkListAdapter.setOnClickDeleteListener { pk, todo ->
                viewModel.deleteCheckList(pk, todo)
                notifyItemChanged(position)
            }

            checkListAdapter.setOnClickModifyListener { pk, todo ->
                viewModel.putCheckList(pk, todo)
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
        holder.bind(getItem(position), position)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.subject.hashCode() == newItem.subject.hashCode()
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }
}