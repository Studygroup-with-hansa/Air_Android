package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.mapper.toEntity
import com.hansarang.android.data.network.remote.WeeklyStatsRemote
import com.hansarang.android.domain.entity.dto.BaseStats
import com.hansarang.android.domain.entity.dto.Stats
import javax.inject.Inject

class WeeklyStatsDataSource @Inject constructor(
    override val remote: WeeklyStatsRemote
): BaseDataSource<WeeklyStatsRemote>() {

    suspend fun getStats(startDate: String, endDate: String): BaseStats {
        return remote.getStats(startDate, endDate).toEntity()
    }

}
