package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.BaseStatsData
import com.hansarang.android.domain.entity.dto.BaseStats
import com.hansarang.android.domain.entity.dto.Stats

fun BaseStatsData.toEntity(): BaseStats {
    return BaseStats(stats.map {
        it.toEntity()
    })
}

fun BaseStats.toData(): BaseStatsData {
    return BaseStatsData(stats.map {
        it.toData()
    })

}