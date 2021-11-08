package com.hansarang.android.air.background.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.hansarang.android.air.R
import com.hansarang.android.air.ui.activity.TimerActivity
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Duration
import java.util.*

@HiltWorker
class TimerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
): CoroutineWorker(context, params) {

    companion object {
        const val TAG = "TimerWorker"
    }

    override suspend fun doWork(): Result = with(applicationContext) {

        val isStarted = inputData.getBoolean("isStarted", false)
        val time = inputData.getLong("totalTime", 0L) + 1
        val goal = inputData.getLong("goal", 0L)
        val title = inputData.getString("title")

        val notificationManager = getSystemService(NotificationManager::class.java)

        val notificationChannel = NotificationChannel(
            getString(R.string.app_name),
            "timer",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)

        val timerIntent = Intent(this, TimerActivity::class.java)
        timerIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP or FLAG_ACTIVITY_NEW_TASK)
        timerIntent.putExtra("totalTime", time)
        timerIntent.putExtra("title", title)
        timerIntent.putExtra("goal", goal)
        timerIntent.putExtra("isStarted", isStarted)

        var sec = time
        var min = sec / 60
        val hour = min / 60
        sec %= 60
        min %= 60

        val timerPendingIntent = PendingIntent.getActivity(
            this@with,
            0,
            timerIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder =
            NotificationCompat.Builder(this, getString(R.string.app_name))
                .setContentText("$title 과목을 공부한 시간")
                .setSmallIcon(R.drawable.ic_logo_main_color)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentIntent(timerPendingIntent)
                .setExtras(
                    bundleOf(
                        "totalTime" to time,
                        "isStarted" to isStarted,
                        "title" to title,
                        "goal" to goal
                    )
                )
                .setContentTitle(
                    String.format(
                        "%02d:%02d:%02d",
                        hour,
                        min,
                        sec
                    )
                )

        notificationManager.notify(1, notificationBuilder.build())

        val data =
            Data.Builder()
                .putLong("totalTime", time)
                .putLong("goal", goal)
                .putString("title", title)
                .putBoolean("isStarted", isStarted)
                .build()

        val timerWorkerRequest = OneTimeWorkRequestBuilder<TimerWorker>()
            .setInputData(data)
            .addTag(TAG)
            .setInitialDelay(Duration.ofMillis(1000L))
            .build()

        WorkManager.getInstance(this)
            .enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, timerWorkerRequest)

        return Result.success(data)
    }
}