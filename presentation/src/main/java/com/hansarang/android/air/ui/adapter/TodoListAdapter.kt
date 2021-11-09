package com.hansarang.android.air.ui.adapter

import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.doOnAttach
import androidx.core.view.doOnDetach
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.ItemTodoBinding
import com.hansarang.android.air.ui.bind.*
import com.hansarang.android.air.ui.decorator.ItemDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.adapter.TodoListAdapterViewModel
import com.hansarang.android.domain.entity.dto.CheckListItem
import com.hansarang.android.domain.entity.dto.Todo
import com.hansarang.android.domain.usecase.checklist.DeleteCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PostCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutModifyCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutStatusChangeCheckListUseCase
import java.text.SimpleDateFormat
import java.util.*

class TodoListAdapter(
    private val postCheckListUseCase: PostCheckListUseCase,
    private val putModifyCheckListUseCase: PutModifyCheckListUseCase,
    private val putStatusChangeCheckListUseCase: PutStatusChangeCheckListUseCase,
    private val deleteCheckListUseCase: DeleteCheckListUseCase
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    private val list = ArrayList<Todo>()

    fun submitList(list: List<Todo>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Todo {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(
        private val binding: ItemTodoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private var lifecycleOwner: LifecycleOwner? = null

        init {
            itemView.doOnAttach {
                lifecycleOwner = itemView.findViewTreeLifecycleOwner()
            }
            itemView.doOnDetach {
                lifecycleOwner = null
            }
        }

        private fun submitCheckList(
            linearLayout: LinearLayout,
            recyclerView: RecyclerView,
            todoList: List<CheckListItem>
        ) = with(binding) {
            var todoDoneCount = 0
            todoList.forEach { if (it.isitDone) todoDoneCount++ }
            val todoListCount = todoList.size
            tvPercentsTodo.text = String.format("%d%% 달성", (todoDoneCount.toFloat() / todoListCount.toFloat()).toInt() * 100)

            recyclerView.run {
                if (itemDecorationCount == 0) addItemDecoration(ItemDividerDecorator(5.dp))
                linearLayout.setPadding(
                    0, 0, 0,
                    if (todoList.isEmpty()) 10.dp else 0
                )
            }
        }

        fun bind(todo: Todo) {
            val viewModel = TodoListAdapterViewModel(
                postCheckListUseCase,
                putModifyCheckListUseCase,
                putStatusChangeCheckListUseCase,
                deleteCheckListUseCase
            )
            val checkListAdapter = CheckListAdapter(viewModel)

            binding.todo = todo
            init(todo, viewModel, checkListAdapter)
            observe(viewModel, checkListAdapter)
        }

        private fun observe(
            viewModel: TodoListAdapterViewModel,
            checkListAdapter: CheckListAdapter
        ) {
            itemView.doOnAttach {
                viewModel.isPostCheckListSuccess.observe(lifecycleOwner!!, EventObserver {
                    val curList = checkListAdapter.addItem(it)
                    setPercents(ArrayList(curList))
                    with(binding) {
                        submitCheckList(
                            linearLayoutHorizontalCheckListTodo,
                            rvCheckListTodo,
                            curList
                        )
                    }
                })
            }
        }

        private fun init(
            todo: Todo,
            viewModel: TodoListAdapterViewModel,
            checkListAdapter: CheckListAdapter
        ) = with(binding) {
            setPercents(ArrayList(todo.todoList))

            ivExpendTodo.setDefaultToggle(todo.isExpended)
            btnExpendTodo.isSelected = todo.isExpended
            if (todo.isExpended) {
                nestedScrollViewCheckListTodo.setExpend()
            } else {
                nestedScrollViewCheckListTodo.setCollapse()
            }

            rvCheckListTodo.run {
                adapter = checkListAdapter
                checkListAdapter.submitList(todo.todoList.toMutableList())
                submitCheckList(
                    linearLayoutHorizontalCheckListTodo,
                    rvCheckListTodo,
                    todo.todoList
                )
            }

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
                    if (editText.text.isNotEmpty()) {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                        val date = sdf.format(Date(System.currentTimeMillis()))
                        viewModel.postCheckList(
                            todo.subject,
                            date,
                            etAddCheckListTodo.text.toString())
                        etAddCheckListTodo.setText("")
                    }
                }
                return@setOnKeyListener true
            }

            checkListAdapter.setOnClickDeleteListener { checkListItem ->
                viewModel.deleteCheckList(checkListItem.pk, checkListItem.todo)
                val curList = checkListAdapter.removeItem(checkListItem)
                setPercents(ArrayList(curList))
                with(binding) {
                    submitCheckList(
                        linearLayoutHorizontalCheckListTodo,
                        rvCheckListTodo,
                        curList
                    )
                }
            }

            checkListAdapter.setOnClickModifyListener { checkListItem, newTitle ->
                viewModel.putCheckList(checkListItem.pk, newTitle)
            }

            checkListAdapter.setOnCheckedChangeListener { checkListItem ->
                val isItDone = checkListItem.isitDone
                val curList = checkListAdapter.getList()
                for ((idx, curItem) in curList.withIndex()) {
                    if (curItem == checkListItem) {
                        checkListItem.isitDone = !isItDone
                        curList[idx] = checkListItem
                        break
                    }
                }
                setPercents(ArrayList(curList))
                viewModel.putStatusChangeCheckList(checkListItem)
            }
        }

        private fun setPercents(checkList: ArrayList<CheckListItem>) = with(binding) {
            itemView.doOnAttach {
                if (checkList.isNotEmpty()) {
                    var doneCount = 0
                    val checkListCount = checkList.size
                    checkList.forEach {
                        if (it.isitDone) {
                            Log.d("TAG", "setPercents: ")
                            doneCount++
                        }
                    }
                    val percents = ((doneCount.toFloat() / checkListCount.toFloat()) * 100).toInt()
                    tvPercentsTodo.text = "$percents% 달성"
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
}