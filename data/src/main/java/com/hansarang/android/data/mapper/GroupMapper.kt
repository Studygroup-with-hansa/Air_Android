package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.GroupData
import com.hansarang.android.domain.entity.dto.Group

fun GroupData.toEntity(): Group {
    return Group(
        this.code,
        this.leader,
        this.leaderEmail,
        this.firstPlace,
        this.firstPlaceStudyTime,
        this.userCount
    )
}

fun Group.toData(): GroupData {
    return GroupData(
        this.code,
        this.leader,
        this.leaderEmail,
        this.firstPlace,
        this.firstPlaceStudyTime,
        this.userCount
    )
}