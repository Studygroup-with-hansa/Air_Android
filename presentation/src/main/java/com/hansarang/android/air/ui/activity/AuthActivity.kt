package com.hansarang.android.air.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivityAuthBinding
import com.hansarang.android.air.ui.util.BackPressedHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var backPressedHandler: BackPressedHandler
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController(binding.navHostFragmentAuth.id)
    }

    override fun onBackPressed() {
        if (navController.previousBackStackEntry == null) {
            if (!this::backPressedHandler.isInitialized)
                backPressedHandler = BackPressedHandler(this)
            backPressedHandler.onBackPressed()
        } else {
            navController.navigateUp()
        }
    }
}