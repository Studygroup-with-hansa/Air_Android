
package com.hansarang.android.air.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.hansarang.android.air.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigationViewMain

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
        bottomNavigationView.setupWithNavController(navController)
    }

}