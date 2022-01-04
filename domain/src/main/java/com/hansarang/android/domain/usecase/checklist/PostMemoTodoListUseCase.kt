package com.hansarang.android.domain.usecase.checklist

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.CheckListRepository
import javax.inject.Inject

class PostMemoTodoListUseCase @Inject constructor(
    private val checkListRepository: CheckListRepository
): BaseParamsUseCase<PostMemoTodoListUseCase.Params, String>() {
    data class Params(
        val date: String,
        val memo: String
    )


    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return checkListRepository.postMemoTodoList(params.date, params.memo)
    }
}