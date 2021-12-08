
package com.hansarang.android.air.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.hansarang.android.air.databinding.ActivityMainBinding
import com.hansarang.android.air.ui.base.BaseActivity
import com.hansarang.android.air.ui.util.BackPressedHandler
import com.hansarang.android.air.ui.viewmodel.activity.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels()

    private lateinit var backPressedHandler: BackPressedHandler
    private lateinit var permissionLauncher: ActivityResultLauncher<Intent>
    private lateinit var navController: NavController

    override fun observerViewModel() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (Settings.canDrawOverlays(this)) {
                val snackBar = Snackbar.make(
                    this,
                    binding.root,
                    "권한이 허용되었습니다.",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBar.setAction("확인") {
                    snackBar.dismiss()
                }.show()
            } else {
                val snackBar = Snackbar.make(
                    this,
                    binding.root,
                    "권한이 허용되지 않았습니다.",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBar
                    .setAction("허용하기") {
                        permissionLauncher.launch(
                            Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                        )
                        snackBar.dismiss()
                    }.show()
            }
        }

        if (!Settings.canDrawOverlays(this)) {
            permissionLauncher.launch(
                Intent(ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            )
        }
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController(binding.navHostFragmentMain.id)
        binding.bottomNavigationViewMain.setupWithNavController(navController)
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