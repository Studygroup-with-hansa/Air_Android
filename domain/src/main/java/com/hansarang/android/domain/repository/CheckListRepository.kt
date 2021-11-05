package com.hansarang.android.domain.repository

import com.hansarang.android.domain.entity.dto.BaseTodo

interface CheckListRepository {
    suspend fun getTodoList(date: String): BaseTodo
    suspend fun putModifyCheckList(pk: Int, todo: String): String
    suspend fun postCheckList(subject: String, date: String, todo: String): String
    suspend fun putStatusChangeCheckList(pk: Int): String
    suspend fun postMemoTodoList(date: String, memo: String): String
}