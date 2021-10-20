package com.hansarang.android.domain.usecase.checklist

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.BaseTodo
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class GetCheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository
): BaseParamsUseCase<GetCheckListUseCase.Params, BaseTodo>() {
    data class Params(
        val date: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): BaseTodo {
        return checkListRepository.getCheckList(params.date)
    }
}