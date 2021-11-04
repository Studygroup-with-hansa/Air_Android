package com.hansarang.android.air.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hansarang.android.air.R
import com.hansarang.android.air.ui.util.BackPressedHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var backPressedHandler: BackPressedHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onBackPressed() {
        if (this::backPressedHandler.isInitialized)
            backPressedHandler = BackPressedHandler(this)
        backPressedHandler.onBackPressed()
    }
}