package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.TodoData
import com.hansarang.android.domain.entity.dto.Todo

fun TodoData.toEntity(): Todo {
    return Todo(
        subject,
        todoList.map {
            it.toEntity()
        }
    )
}

fun Todo.toData(): TodoData {
    return TodoData(
        subject,
        todoList.map {
            it.toData()
        }
    )
}