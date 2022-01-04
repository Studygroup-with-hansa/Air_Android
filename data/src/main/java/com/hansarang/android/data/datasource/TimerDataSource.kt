package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.network.remote.TimerRemote
import javax.inject.Inject

class TimerDataSource @Inject constructor(
    override val remote: TimerRemote
): BaseDataSource<TimerRemote>() {
    suspend fun postTimerStart(title: String): String {
        return remote.postTimerStart(title)
    }

    suspend fun postTimerStop(title: String): String {
        return remote.postTimerStop(title)
    }
}