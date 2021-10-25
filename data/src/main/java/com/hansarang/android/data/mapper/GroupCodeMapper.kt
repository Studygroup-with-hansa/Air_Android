package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.GroupCodeData
import com.hansarang.android.domain.entity.dto.GroupCode

fun GroupCodeData.toEntity(): GroupCode {
    return GroupCode(this.code)
}

fun GroupCode.toData(): GroupCodeData {
    return GroupCodeData(this.code)
}