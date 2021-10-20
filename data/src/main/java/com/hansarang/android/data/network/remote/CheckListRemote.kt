package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.entity.BaseTodoData
import com.hansarang.android.data.network.service.CheckListService
import javax.inject.Inject

class CheckListRemote @Inject constructor(
    override val service: CheckListService
): BaseRemote<CheckListService>() {
    suspend fun getCheckList(date: String): BaseTodoData {
        return getResponse(service.getCheckList(date))
    }
}