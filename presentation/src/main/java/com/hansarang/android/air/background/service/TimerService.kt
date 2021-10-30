package com.hansarang.android.air.background.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.hansarang.android.air.R
import com.hansarang.android.air.ui.activity.TimerActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class TimerService : Service() {

    private lateinit var timer: Timer
    private lateinit var timerTask: TimerTask
    private lateinit var title: String
    private var time = 0L
    private var goal = 0L

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        title = intent?.getStringExtra("title") ?: ""
        time = intent?.getLongExtra("totalTime", 0L) ?: 0L
        goal = intent?.getLongExtra("goal", 0L) ?: 0L

        val versionChk = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

        val timerIntent = Intent(this, TimerActivity::class.java)
        val timerPendingIntent =
            PendingIntent.getActivity(
                this,
                0,
                timerIntent,
                if (versionChk) PendingIntent.FLAG_IMMUTABLE else 0
            )

        val notificationBuilder =
            NotificationCompat.Builder(this, getString(R.string.app_name))
                .setContentText("$title 과목을 공부한 시간")
                .setSmallIcon(R.drawable.ic_logo_main_color)
                .setContentIntent(timerPendingIntent)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)

        val notificationChannel = NotificationChannel(getString(R.string.app_name), "timer", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)

        timer = Timer()
        timerTask = timerTask {
            if (goal > time) {
                time += 1

                var sec = time
                var min = sec / 60
                val hour = min / 60
                sec %= 60
                min %= 60

                notificationBuilder.setContentTitle(String.format("%02d:%02d:%02d", hour, min, sec))
                notificationManager.notify(1, notificationBuilder.build())
            } else {
                timer.cancel()
                timerIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                timerIntent.putExtra("totalTime", time)
                timerIntent.putExtra("title", title)
                timerIntent.putExtra("goal", goal)
                startActivity(timerIntent)
                stopForeground(true)
            }
        }
        startForeground(1, notificationBuilder.build())
        timer.schedule(timerTask, 0L, 1000L)

        return super.onStartCommand(intent, flags, startId)
    }

}