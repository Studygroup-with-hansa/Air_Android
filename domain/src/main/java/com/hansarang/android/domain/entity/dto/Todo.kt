package com.hansarang.android.domain.entity.dto

data class Todo(
    val subject: String,
    val todoList: List<TodoListItem>,
    var isExpended: Boolean = false
)