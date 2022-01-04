package com.hansarang.android.domain.repository

import com.hansarang.android.domain.entity.dto.BaseStats
import com.hansarang.android.domain.entity.dto.Stats

interface WeeklyStatsRepository {
    suspend fun getStats(startDate: String, endDate: String): BaseStats
}