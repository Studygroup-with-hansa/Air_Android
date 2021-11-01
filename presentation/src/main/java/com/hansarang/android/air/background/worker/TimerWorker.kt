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
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hansarang.android.air.R
import com.hansarang.android.air.ui.activity.TimerActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import java.util.*
import android.app.AlarmManager
import androidx.lifecycle.MutableLiveData


@HiltWorker
class TimerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
): CoroutineWorker(context, params) {

    companion object {
        var time: Long = 0
        const val TAG = "TimerWorker"
    }

    override suspend fun doWork(): Result = with(applicationContext) {
        return try {
            time = inputData.getLong("totalTime", 0L)
            val goal = inputData.getLong("goal", 0L)
            val title = inputData.getString("title")
            val isStarted = true
            val notificationManager = getSystemService(NotificationManager::class.java)

            val notificationChannel = NotificationChannel(
                getString(R.string.app_name),
                "timer",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)

            while (true) {

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
                        .setContentTitle(
                            String.format(
                                "%02d:%02d:%02d",
                                hour,
                                min,
                                sec
                            )
                        )
                if (goal > time) {
                    notificationManager.notify(1, notificationBuilder.build())
                } else {
                    notificationManager.cancel(1)
                    startActivity(timerIntent)
                    break
                }

                time += 1
                delay(1000L)
            }
            time = 0

            Result.success()
        } catch (e: CancellationException) {
            Log.d(TAG, "doWork: cancelled")
            Result.success()
        }
    }

}