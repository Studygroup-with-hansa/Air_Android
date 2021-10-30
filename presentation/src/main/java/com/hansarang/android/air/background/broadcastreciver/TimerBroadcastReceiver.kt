package com.hansarang.android.air.background.broadcastreciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TimerBroadcastReceiver(): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        GlobalScope.launch(Dispatchers.IO) {

        }.cancel()
    }
}