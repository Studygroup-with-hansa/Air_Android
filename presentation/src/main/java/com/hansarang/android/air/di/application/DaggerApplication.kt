package com.hansarang.android.air.di.application

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.hansarang.android.air.background.worker.TimerWorker
import com.hansarang.android.air.ui.util.SharedPreferenceHelper
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.init
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DaggerApplication(): Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration
            .Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        init(this)

        when(SharedPreferenceHelper.nightMode) {
            SharedPreferenceHelper.LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO)
            SharedPreferenceHelper.DARK_MODE -> AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES)
            SharedPreferenceHelper.SYSTEM_MODE -> {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        super.onCreate()

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.cancelAllWorkByTag(TimerWorker.TAG)

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancel(1)
    }
}