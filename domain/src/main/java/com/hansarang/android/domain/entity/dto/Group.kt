package com.hansarang.android.domain.entity.dto

data class Group(
    val code: String,
    val leader: String,
    val firstPlace: String,
    val firstPlaceStudyTime: Long,
    val userCount: Int
)