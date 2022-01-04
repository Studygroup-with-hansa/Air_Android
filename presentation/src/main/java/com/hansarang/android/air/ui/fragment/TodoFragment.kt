package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.FragmentTodoBinding
import com.hansarang.android.air.ui.adapter.TodoListAdapter
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.decorator.ItemDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.fragment.TodoViewModel
import com.hansarang.android.domain.usecase.checklist.DeleteCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PostCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutModifyCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutStatusChangeCheckListUseCase
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class TodoFragment : BaseFragment<FragmentTodoBinding, TodoViewModel>() {

    @Inject lateinit var todoListAdapter: TodoListAdapter
    override val viewModel: TodoViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        viewModel.getTodos()
    }

    override fun observerViewModel() {
        binding.rvTodoListTodo.adapter = todoListAdapter
        binding.rvTodoListTodo.addItemDecoration(ItemDividerDecorator(5.dp))
        binding.rvTodoListTodo.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.srlTodo.isEnabled = !recyclerView.canScrollVertically(-1)
            }
        })

        binding.etMemoTodo.setOnEditorActionListener { view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_SEND
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER) {

                viewModel.postMemo()
            }
            return@setOnEditorActionListener true
        }

        viewModel.isSuccess.observe(viewLifecycleOwner) {
            todoListAdapter.submitList(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.sflTodo.startShimmer()
            } else {
                binding.sflTodo.stopShimmer()
                binding.srlTodo.isRefreshing = false
                binding.nestedScrollViewTodo.fullScroll(NestedScrollView.FOCUS_UP)
            }
        }

        viewModel.isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        })
    }
}