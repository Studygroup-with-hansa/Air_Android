package com.hansarang.android.air.background.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.work.WorkManager
import com.hansarang.android.air.R
import com.hansarang.android.air.background.worker.TimerWorker
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundTimerForceQuitService: Service() {

    @Inject
    lateinit var postTimerStopUseCase: PostTimerStopUseCase

    var title: String = ""

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        title = intent?.getStringExtra("title") ?: ""

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        runBlocking {
            val params = PostTimerStopUseCase.Params(title)
            postTimerStopUseCase.buildParamsUseCaseSuspend(params)
            val workManager = WorkManager.getInstance(applicationContext)
            workManager.cancelAllWorkByTag(TimerWorker.TAG)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.cancel(1)

            stopSelf()
        }
    }

}