package com.hansarang.android.domain.entity.response

data class BaseResponse<T>(
    val status: Int,
    val detail: String,
    val data: T
)