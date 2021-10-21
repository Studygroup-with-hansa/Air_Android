package com.hansarang.android.domain.entity.dto

data class GroupRank(
    val name: String,
    val email: String,
    val profileImg: String,
    val todayStudyTime: Long,
    val isItOwner: Boolean,
    val rank: Int
)