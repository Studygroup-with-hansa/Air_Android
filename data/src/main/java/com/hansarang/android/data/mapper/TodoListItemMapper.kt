package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.TodoListItemData
import com.hansarang.android.domain.entity.dto.TodoListItem

fun TodoListItemData.toEntity(): TodoListItem {
    return TodoListItem(
        this.isitDone,
        this.todo
    )
}

fun TodoListItem.toData(): TodoListItemData {
    return TodoListItemData(
        this.isitDone,
        this.todo
    )
}