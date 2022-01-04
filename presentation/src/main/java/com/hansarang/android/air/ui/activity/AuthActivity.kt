package com.hansarang.android.air.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivityAuthBinding
import com.hansarang.android.air.ui.base.BaseActivity
import com.hansarang.android.air.ui.util.BackPressedHandler
import com.hansarang.android.air.ui.viewmodel.activity.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()
    private lateinit var backPressedHandler: BackPressedHandler
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()
        navController = findNavController(binding.navHostFragmentAuth.id)
    }

    override fun observerViewModel() {}

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