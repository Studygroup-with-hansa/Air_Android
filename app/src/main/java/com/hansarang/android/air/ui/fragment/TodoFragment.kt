package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hansarang.android.air.databinding.FragmentTodoBinding
import com.hansarang.android.air.ui.viewmodel.factory.TodoViewModelFactory
import com.hansarang.android.air.ui.viewmodel.fragment.TodoViewModel

class TodoFragment : Fragment() {

    private lateinit var binding: FragmentTodoBinding
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
    }

}