package com.hansarang.android.air.di.application

import android.app.Application
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.init
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DaggerApplication(): Application() {
    override fun onCreate() {
        init(this)
        super.onCreate()
    }
}