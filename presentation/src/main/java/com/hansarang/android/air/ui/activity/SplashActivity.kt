package com.hansarang.android.air.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.ActivitySplashBinding
import com.hansarang.android.air.ui.base.BaseActivity
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.token
import com.hansarang.android.air.ui.viewmodel.activity.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModels()

    override fun observerViewModel() {
        viewModel.isSuccess.observe(this@SplashActivity) {
            val intent = Intent(this@SplashActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewModel.isFailure.observe(this@SplashActivity) {
            Toast.makeText(this@SplashActivity, it, Toast.LENGTH_SHORT).show()
            val intent = Intent(this@SplashActivity, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (token.isNotEmpty()) {
            viewModel.checkToken()
        }
    }
}