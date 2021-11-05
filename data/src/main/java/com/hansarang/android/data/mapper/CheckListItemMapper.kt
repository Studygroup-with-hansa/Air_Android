package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.CheckListItemData
import com.hansarang.android.domain.entity.dto.CheckListItem

fun CheckListItemData.toEntity(): CheckListItem {
    return CheckListItem(
        this.pk,
        this.isitDone,
        this.todo
    )
}

fun CheckListItem.toData(): CheckListItemData {
    return CheckListItemData(
        this.pk,
        this.isitDone,
        this.todo
    )
}