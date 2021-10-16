package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.entity.BaseStatsData
import com.hansarang.android.data.entity.StatsData
import com.hansarang.android.data.network.service.WeeklyStatsService
import javax.inject.Inject

class WeeklyStatsRemote @Inject constructor(
    override val service: WeeklyStatsService
): BaseRemote<WeeklyStatsService>() {
    suspend fun getStats(startDate: String, endDate: String): List<StatsData> {
        return getResponse(service.getStats(startDate, endDate)).stats
    }
}