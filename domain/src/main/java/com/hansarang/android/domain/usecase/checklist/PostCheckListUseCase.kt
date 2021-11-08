package com.hansarang.android.domain.usecase.checklist

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.CheckListPk
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class PostCheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository
): BaseParamsUseCase<PostCheckListUseCase.Params, CheckListPk>() {
    data class Params(
        val subject: String,
        val date: String,
        val todo: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): CheckListPk {
        return checkListRepository.postCheckList(params.subject, params.date, params.todo)
    }
}