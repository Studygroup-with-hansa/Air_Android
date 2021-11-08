package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.mapper.toEntity
import com.hansarang.android.data.network.remote.CheckListRemote
import com.hansarang.android.domain.entity.dto.BaseTodo
import com.hansarang.android.domain.entity.dto.CheckListPk
import javax.inject.Inject

class CheckListDataSource @Inject constructor(
    override val remote: CheckListRemote
): BaseDataSource<CheckListRemote>() {
    suspend fun getTodoList(date: String): BaseTodo {
        return remote.getTodoList(date).toEntity()
    }

    suspend fun putModifyCheckList(pk: Int, todo: String): String {
        return remote.putModifyCheckList(pk, todo)
    }

    suspend fun postCheckList(
        subject: String,
        date: String,
        todo: String
    ): CheckListPk {
        return remote.postCheckList(subject, date, todo).toEntity()
    }

    suspend fun putStatusChangeCheckList(pk: Int): String {
        return remote.putStatusChangeCheckList(pk)
    }

    suspend fun deleteCheckList(pk: Int): String {
        return remote.deleteCheckList(pk)
    }

    suspend fun postMemoTodoList(date: String, memo: String): String {
        return remote.postMemoTodoList(date, memo)
    }
}