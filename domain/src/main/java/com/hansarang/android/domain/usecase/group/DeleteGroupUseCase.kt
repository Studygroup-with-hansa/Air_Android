package com.hansarang.android.domain.usecase.group

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.repository.GroupRepository
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(
    private val groupRepository: GroupRepository
): BaseUseCase<String>() {
    override suspend fun buildUseCaseSuspend(): String {
        return groupRepository.deleteGroup()
    }

}