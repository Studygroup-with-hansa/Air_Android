package com.hansarang.android.domain.entity.dto

data class BaseTodo(
    val date: String,
    val memo: String,
    val subjects: List<Todo>
)