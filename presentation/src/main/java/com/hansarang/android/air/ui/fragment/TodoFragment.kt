package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.databinding.FragmentTodoBinding
import com.hansarang.android.air.ui.adapter.TodoListAdapter
import com.hansarang.android.air.ui.decorator.ItemDividerDecorator
import com.hansarang.android.air.ui.extention.dp
import com.hansarang.android.air.ui.viewmodel.factory.TodoViewModelFactory
import com.hansarang.android.air.ui.viewmodel.fragment.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodoFragment : Fragment() {

    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var binding: FragmentTodoBinding
    private lateinit var recyclerView: RecyclerView
    private val factory by lazy { TodoViewModelFactory() }
    private val viewModel: TodoViewModel by viewModels { factory }

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

        viewModel.getTodoList()

        init()
        observe()
    }

    private fun observe() = with(viewModel) {
        todoList.observe(viewLifecycleOwner) {
            todoListAdapter.submitList(it)
        }
    }

    private fun init() {
        recyclerView = binding.rvTodoListTodo
        todoListAdapter = TodoListAdapter(viewModel)
        recyclerView.adapter = todoListAdapter
        recyclerView.addItemDecoration(ItemDividerDecorator(5.dp))
    }

}