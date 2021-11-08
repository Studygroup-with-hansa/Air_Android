package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.CheckListPkData
import com.hansarang.android.domain.entity.dto.CheckListPk

fun CheckListPkData.toEntity(): CheckListPk {
    return CheckListPk(
        this.pk
    )
}

fun CheckListPk.toData(): CheckListPkData {
    return CheckListPkData(
        this.pk
    )
}