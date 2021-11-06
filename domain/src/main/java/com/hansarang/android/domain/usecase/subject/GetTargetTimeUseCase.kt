package com.hansarang.android.domain.usecase.subject

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.entity.dto.TargetTime
import com.hansarang.android.domain.repository.SubjectRepository
import javax.inject.Inject

class GetTargetTimeUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
): BaseUseCase<TargetTime>() {
    override suspend fun buildUseCaseSuspend(): TargetTime {
        return subjectRepository.getTargetTime()
    }
}