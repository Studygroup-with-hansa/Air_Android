package com.hansarang.android.data.entity

data class GroupRankData(
    val name: String,
    val email: String,
    val profileImg: String,
    val todayStudyTime: Long,
    val isItOwner: Boolean,
    val rank: Int
)