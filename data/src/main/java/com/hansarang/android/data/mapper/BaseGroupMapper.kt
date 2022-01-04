package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.BaseGroupData
import com.hansarang.android.domain.entity.dto.BaseGroup

fun BaseGroupData.toEntity(): BaseGroup {
    return BaseGroup(
        this.groupList.map {
            it.toEntity()
        }
    )
}

fun BaseGroup.toData(): BaseGroupData {
    return BaseGroupData(
        this.groupList.map {
            it.toData()
        }
    )
}