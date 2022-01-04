package com.hansarang.android.domain.usecase.group

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.BaseGroupDetail
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class PostViewGroupDetail @Inject constructor(
    private val groupRepository: GroupRepository
): BaseParamsUseCase<PostViewGroupDetail.Params, BaseGroupDetail>() {
    data class Params(
        val groupCode: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): BaseGroupDetail {
        return groupRepository.postViewGroupDetail(params.groupCode)
    }
}