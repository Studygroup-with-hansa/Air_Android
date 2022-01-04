package com.hansarang.android.domain.usecase.group

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class DeleteLeaveGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
): BaseParamsUseCase<DeleteLeaveGroupUseCase.Params, String>() {
    data class Params(val groupCode: String)

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return groupRepository.deleteLeaveGroup(params.groupCode)
    }
}