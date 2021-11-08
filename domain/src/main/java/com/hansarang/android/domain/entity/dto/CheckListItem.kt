package com.hansarang.android.domain.entity.dto

data class CheckListItem (
    val pk: Int,
    val todo: String,
    val isitDone: Boolean = false,
)