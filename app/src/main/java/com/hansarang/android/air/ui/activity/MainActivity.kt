
package com.hansarang.android.air.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.Toolbar
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout

    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayoutMain
        bottomNavigationView = binding.bottomNavigationViewMain
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.memoFragment,
                R.id.todoFragment,
                R.id.groupFragment,
                R.id.statsFragment
            ),
            drawerLayout
        )
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController(binding.navHostFragmentMain.id)
        bottomNavigationView.setupWithNavController(navController)
    }

}