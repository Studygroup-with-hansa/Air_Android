package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.GroupRankData
import com.hansarang.android.domain.entity.dto.GroupRank

fun GroupRankData.toEntity(): GroupRank {
    return GroupRank(
        this.name,
        this.email,
        this.profileImg,
        this.todayStudyTime,
        this.isItOwner,
        this.rank
    )
}

fun GroupRank.toData(): GroupRankData {
    return GroupRankData(
        this.name,
        this.email,
        this.profileImg,
        this.todayStudyTime,
        this.isItOwner,
        this.rank
    )
}