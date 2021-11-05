package com.hansarang.android.domain.usecase.checklist

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class PutModifyCheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository
): BaseParamsUseCase<PutModifyCheckListUseCase.Params, String>() {
    data class Params(
        val pk: Int,
        val todo: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return checkListRepository.putModifyCheckList(params.pk, params.todo)
    }
}