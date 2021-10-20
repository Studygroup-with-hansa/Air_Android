package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.CheckListDataSource
import com.hansarang.android.domain.entity.dto.BaseTodo
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class CheckListRepositoryImpl @Inject constructor(
    private val checkListDataSource: CheckListDataSource
): CheckListRepository {
    override suspend fun getCheckList(date: String): BaseTodo {
        return checkListDataSource.getCheckList(date)
    }
}