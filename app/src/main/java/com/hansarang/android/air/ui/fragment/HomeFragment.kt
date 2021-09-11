package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        toolbar = binding.toolbarHome
        drawerLayout = binding.drawerLayoutHome

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