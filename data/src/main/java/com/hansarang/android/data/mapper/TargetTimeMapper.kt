package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.TargetTimeData
import com.hansarang.android.domain.entity.dto.TargetTime

fun TargetTimeData.toEntity(): TargetTime {
    return TargetTime(
        targetTime
    )
}

fun TargetTime.toData(): TargetTimeData {
    return TargetTimeData(
        targetTime
    )
}