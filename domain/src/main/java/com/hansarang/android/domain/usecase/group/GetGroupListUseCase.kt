package com.hansarang.android.domain.usecase.group

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.entity.dto.BaseGroup
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupListUseCase @Inject constructor(
    private val groupRepository: GroupRepository
): BaseUseCase<BaseGroup>() {
    override suspend fun buildUseCaseSuspend(): BaseGroup {
        return groupRepository.getGroupList()
    }
}