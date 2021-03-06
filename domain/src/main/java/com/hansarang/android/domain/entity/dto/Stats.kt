package com.hansarang.android.domain.entity.dto

data class Stats(
    val date: String,
    val achievementRate: Float,
    val goal: Int,
    val subject: List<Subject>,
    val totalStudyTime: Int,
)