package com.hansarang.android.air.background.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.hansarang.android.air.R
import com.hansarang.android.air.background.worker.TimerWorker
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundTimerForceQuitService: Service() {

    @Inject
    lateinit var postTimerStopUseCase: PostTimerStopUseCase

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        runBlocking {
            for (it in notificationManager.activeNotifications) {
                val notification = it.notification
                if (notification.channelId == getString(R.string.app_name)) {
                    val title = notification.extras.getString("title", "")
                    val params = PostTimerStopUseCase.Params(title)
                    postTimerStopUseCase.buildParamsUseCaseSuspend(params)
                    break
                }
            }
            val workManager = WorkManager.getInstance(applicationContext)
            workManager.cancelAllWorkByTag(TimerWorker.TAG)
            notificationManager.cancel(1)
            stopSelf()
        }
    }

}