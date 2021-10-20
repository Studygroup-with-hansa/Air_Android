package com.hansarang.android.data.entity

data class BaseTodoData(
    val date: String,
    val memo: String,
    val subjects: List<TodoData>
)