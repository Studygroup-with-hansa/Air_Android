package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.BaseGroupDetailData
import com.hansarang.android.domain.entity.dto.BaseGroupDetail

fun BaseGroupDetailData.toEntity(): BaseGroupDetail {
    return BaseGroupDetail(
        this.code,
        this.userList.map {
            it.toEntity()
        }
    )
}

fun BaseGroupDetail.toData(): BaseGroupDetailData {
    return BaseGroupDetailData(
        this.code,
        this.userList.map {
            it.toData()
        }
    )
}