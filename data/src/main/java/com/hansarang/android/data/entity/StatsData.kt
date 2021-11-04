package com.hansarang.android.data.entity

data class StatsData(
    val date: String,
    val achievementRate: Float,
    val goal: Int,
    val subject: List<SubjectData>,
    val totalStudyTime: Int
)