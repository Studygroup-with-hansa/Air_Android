package com.hansarang.android.domain.usecase.checklist

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class DeleteCheckListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository
): BaseParamsUseCase<DeleteCheckListUseCase.Params, String>() {
    data class Params(
        val pk: Int
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return checkListRepository.deleteCheckList(params.pk)
    }
}