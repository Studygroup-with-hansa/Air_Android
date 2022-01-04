package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.TimerDataSource
import com.hansarang.android.domain.repository.TimerRepository
import javax.inject.Inject

class TimerRepositoryImpl @Inject constructor(
    private val timerDataSource: TimerDataSource
): TimerRepository {
    override suspend fun postTimerStart(title: String): String {
        return timerDataSource.postTimerStart(title)
    }

    override suspend fun postTimerStop(title: String): String {
        return timerDataSource.postTimerStop(title)
    }
}