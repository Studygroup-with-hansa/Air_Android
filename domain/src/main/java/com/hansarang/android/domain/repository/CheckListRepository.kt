package com.hansarang.android.domain.repository

import com.hansarang.android.domain.entity.dto.BaseTodo

interface CheckListRepository {
    suspend fun getCheckList(date: String): BaseTodo
}