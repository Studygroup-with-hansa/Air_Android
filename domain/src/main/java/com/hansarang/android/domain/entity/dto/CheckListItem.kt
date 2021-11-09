package com.hansarang.android.domain.entity.dto

data class CheckListItem (
    val pk: Int,
    var todo: String,
    var isitDone: Boolean = false,
)