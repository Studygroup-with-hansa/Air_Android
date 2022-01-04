package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.CheckListDataSource
import com.hansarang.android.domain.entity.dto.BaseTodo
import com.hansarang.android.domain.entity.dto.CheckListPk
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val checkListDataSource: CheckListDataSource
): CheckListRepository {
    override suspend fun getTodoList(date: String): BaseTodo {
        return checkListDataSource.getTodoList(date)
    }

    override suspend fun putModifyCheckList(pk: Int, todo: String): String {
        return checkListDataSource.putModifyCheckList(pk, todo)
    }

    override suspend fun postCheckList(
        subject: String,
        date: String,
        todo: String
    ): CheckListPk {
        return checkListDataSource.postCheckList(subject, date, todo)
    }

    override suspend fun putStatusChangeCheckList(pk: Int): String {
        return checkListDataSource.putStatusChangeCheckList(pk)
    }

    override suspend fun deleteCheckList(pk: Int): String {
        return checkListDataSource.deleteCheckList(pk)
    }

    override suspend fun postMemoTodoList(date: String, memo: String): String {
        return checkListDataSource.postMemoTodoList(date, memo)
    }
}