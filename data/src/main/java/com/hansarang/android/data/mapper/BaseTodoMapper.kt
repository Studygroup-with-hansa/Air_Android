package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.BaseTodoData
import com.hansarang.android.domain.entity.dto.BaseTodo

fun BaseTodoData.toEntity(): BaseTodo {
    return BaseTodo(
        this.date,
        this.memo,
        this.subjects.map {
            it.toEntity()
        }
    )
}

fun BaseTodo.toData(): BaseTodoData {
    return BaseTodoData(
        this.date,
        this.memo,
        this.subjects.map {
            it.toData()
        }
    )
}