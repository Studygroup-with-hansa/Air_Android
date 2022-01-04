package com.hansarang.android.domain.usecase.group

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.entity.dto.GroupCode
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class PostCreateUserGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
): BaseUseCase<GroupCode>() {
    override suspend fun buildUseCaseSuspend(): GroupCode {
        return groupRepository.postCreateUserGroup()
    }
}