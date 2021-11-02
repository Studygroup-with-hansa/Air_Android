package com.hansarang.android.air.background.broadcast

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_SHUTDOWN
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.WorkManager
import com.hansarang.android.air.background.worker.TimerWorker
import com.hansarang.android.domain.usecase.timer.PostTimerStartUseCase
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class ShutDownReceiver @Inject constructor(
    private val postTimerStopUseCase: PostTimerStopUseCase,
    private val postTimerStartUseCase: PostTimerStartUseCase
): BroadcastReceiver() {

    @DelicateCoroutinesApi
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager = it.getSystemService(NotificationManager::class.java)
            if (notificationManager.activeNotifications.isNotEmpty()) {
                notificationManager.activeNotifications.forEach { statusBarNotification ->
                    with(statusBarNotification) {
                        if (this.id == 1) {
                            GlobalScope.launch(Dispatchers.IO) {
                                val action = intent?.action
                                val extras = statusBarNotification.notification.extras
                                val title = extras.getString("title", "")
                                if (action == ACTION_SHUTDOWN) {
                                    postTimerStopUseCase.buildParamsUseCaseSuspend(
                                        PostTimerStopUseCase.Params(title)
                                    )
                                } else if (action == ACTION_BOOT_COMPLETED) {
                                    postTimerStartUseCase.buildParamsUseCaseSuspend(
                                        PostTimerStartUseCase.Params(title)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}