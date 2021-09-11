package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentHomeBinding
import com.hansarang.android.air.ui.adapter.TimerSubjectListAdapter
import com.hansarang.android.air.ui.viewmodel.factory.HomeViewModelFactory
import com.hansarang.android.air.ui.viewmodel.fragment.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var timerSubjectListAdapter: TimerSubjectListAdapter

    private val factory by lazy { HomeViewModelFactory() }
    private val viewModel: HomeViewModel by viewModels { factory }

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
        viewModel.getSubjectList()
    }

    private fun observe() = with(viewModel) {
        subjectList.observe(viewLifecycleOwner) {
            timerSubjectListAdapter.submitList(it)
        }
    }

    private fun init() {
        toolbar = binding.toolbarHome
        drawerLayout = binding.drawerLayoutHome
        recyclerView = binding.rvSubjectHome
        timerSubjectListAdapter = TimerSubjectListAdapter(viewModel)
        recyclerView.adapter = timerSubjectListAdapter

        with(requireActivity() as AppCompatActivity) {
            setSupportActionBar(toolbar)
            NavigationUI.setupWithNavController(
                toolbar,
                navController,
                drawerLayout
            )
            supportActionBar?.title = ""
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        }
    }

}