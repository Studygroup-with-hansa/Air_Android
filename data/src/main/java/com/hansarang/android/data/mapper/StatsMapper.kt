package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.StatsData
import com.hansarang.android.domain.entity.dto.Stats

fun StatsData.toEntity(): Stats {
    return Stats(
        this.date,
        this.goal,
        this.subject?.map {
            it.toEntity()
        },
        this.totalStudyTime
    )
}

fun Stats.toData(): StatsData {
    return StatsData(
        this.date,
        this.goal,
        this.subject?.map {
            it.toData()
        },
        this.totalStudyTime
    )
}