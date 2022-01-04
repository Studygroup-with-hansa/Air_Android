package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.WeeklyStatsDataSource
import com.hansarang.android.data.entity.StatsData
import com.hansarang.android.domain.entity.dto.BaseStats
import com.hansarang.android.domain.entity.dto.Stats
import com.hansarang.android.domain.repository.WeeklyStatsRepository
import javax.inject.Inject

class WeeklyStatsRepositoryImpl @Inject constructor(
    private val weeklyStatsDataSource: WeeklyStatsDataSource
): WeeklyStatsRepository {
    override suspend fun getStats(startDate: String, endDate: String): BaseStats {
        return weeklyStatsDataSource.getStats(startDate, endDate)
    }
}