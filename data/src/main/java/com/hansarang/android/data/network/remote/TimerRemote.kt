package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.network.service.TimerService
import javax.inject.Inject

class TimerRemote @Inject constructor(
    override val service: TimerService
): BaseRemote<TimerService>() {
    suspend fun postTimerStart(title: String): String {
        return getDetail(service.postTimerStart(title))
    }

    suspend fun postTimerStop(title: String): String {
        return getDetail(service.postTimerStop(title))
    }
}