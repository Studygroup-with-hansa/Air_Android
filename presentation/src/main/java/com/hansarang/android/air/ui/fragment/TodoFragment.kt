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
class TodoFragment : Fragment() {

    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var binding: FragmentTodoBinding
    private lateinit var recyclerView: RecyclerView
    private val viewModel: TodoViewModel by viewModels()

    @Inject
    lateinit var postCheckListUseCase: PostCheckListUseCase
    @Inject
    lateinit var putModifyCheckListUseCase: PutModifyCheckListUseCase
    @Inject
    lateinit var putStatusChangeCheckListUseCase: PutStatusChangeCheckListUseCase
    @Inject
    lateinit var deleteCheckListUseCase: DeleteCheckListUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTodos()

        init()
        observe()
        listener()
    }

    private fun listener() = with(binding) {
        srlTodo.setOnRefreshListener {
            viewModel.getTodos()
        }
        etMemoTodo.setOnEditorActionListener { view, actionId, keyEvent ->
            val editText = view as EditText
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_SEND
                || keyEvent.action == KeyEvent.ACTION_DOWN
                || keyEvent.action == KeyEvent.KEYCODE_ENTER) {

                viewModel.postMemo()
            }
            return@setOnEditorActionListener true
        }
    }

    private fun observe() = with(viewModel) {
        isSuccess.observe(viewLifecycleOwner) {
            todoListAdapter.submitList(it)
        }
        isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.sflTodo.startShimmer()
            } else {
                binding.sflTodo.stopShimmer()
                binding.srlTodo.isRefreshing = false
                binding.nestedScrollViewTodo.fullScroll(NestedScrollView.FOCUS_UP)
            }
        }
        isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        })
    }

    private fun init() {
        recyclerView = binding.rvTodoListTodo
        todoListAdapter = TodoListAdapter(
            postCheckListUseCase,
            putModifyCheckListUseCase,
            putStatusChangeCheckListUseCase,
            deleteCheckListUseCase
        )
        recyclerView.adapter = todoListAdapter
        recyclerView.addItemDecoration(ItemDividerDecorator(5.dp))
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.srlTodo.isEnabled = !recyclerView.canScrollVertically(-1)
            }
        })
    }

}