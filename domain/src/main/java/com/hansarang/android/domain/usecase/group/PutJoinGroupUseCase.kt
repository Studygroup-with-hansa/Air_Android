package com.hansarang.android.domain.usecase.group

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.GroupCode
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class PutJoinGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
): BaseParamsUseCase<PutJoinGroupUseCase.Params, GroupCode>() {
    data class Params(
        val groupCode: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): GroupCode {
        return groupRepository.putJoinGroup(params.groupCode)
    }
}