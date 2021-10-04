package com.hansarang.android.domain.entity.dto

data class Todo(
    val title: String,
    val checkList: ArrayList<String>,
    var isExpended: Boolean = false
)