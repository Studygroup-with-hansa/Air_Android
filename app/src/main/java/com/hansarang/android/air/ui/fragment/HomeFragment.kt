package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentHomeBinding
import com.hansarang.android.air.ui.activity.MainActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var toolbar: Toolbar
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

        if (requireActivity() is MainActivity) {
            with(requireActivity() as MainActivity) {
                setSupportActionBar(toolbar)
                NavigationUI.setupWithNavController(
                    toolbar,
                    navController,
                    appBarConfiguration
                )
                supportActionBar?.title = ""
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            }
        }
    }

}