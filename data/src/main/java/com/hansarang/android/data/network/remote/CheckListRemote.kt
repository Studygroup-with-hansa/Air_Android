package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.entity.BaseTodoData
import com.hansarang.android.data.entity.CheckListPkData
import com.hansarang.android.data.network.service.CheckListService
import javax.inject.Inject

class CheckListRemote @Inject constructor(
    override val service: CheckListService
): BaseRemote<CheckListService>() {
    suspend fun getTodoList(date: String): BaseTodoData {
        return getResponse(service.getTodoList(date))
    }

    suspend fun putModifyCheckList(pk: Int, todo: String): String {
        return getDetail(service.putModifyCheckList(pk, todo))
    }

    suspend fun postCheckList(
        subject: String,
        date: String,
        todo: String
    ): CheckListPkData {
        return getResponse(service.postCheckList(subject, date, todo))
    }

    suspend fun putStatusChangeCheckList(pk: Int): String {
        return getDetail(service.putStatusChangeCheckList(pk))
    }

    suspend fun deleteCheckList(pk: Int): String {
        return getDetail(service.deleteSubject(pk))
    }

    suspend fun postMemoTodoList(date: String, memo: String): String {
        return getDetail(service.postMemoTodoList(date, memo))
    }
}