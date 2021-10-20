package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.mapper.toEntity
import com.hansarang.android.data.network.remote.CheckListRemote
import com.hansarang.android.domain.entity.dto.BaseTodo
import javax.inject.Inject

class CheckListDataSource @Inject constructor(
    override val remote: CheckListRemote
): BaseDataSource<CheckListRemote>() {
    suspend fun getCheckList(date: String): BaseTodo {
        return remote.getCheckList(date).toEntity()
    }
}