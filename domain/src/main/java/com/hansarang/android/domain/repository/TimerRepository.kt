package com.hansarang.android.domain.repository

interface TimerRepository {
    suspend fun postTimerStart(title: String): String
    suspend fun postTimerStop(title: String): String
}