package com.hansarang.android.domain.entity.dto

data class Todo(
    val title: String,
    var isExpended: Boolean = false
)