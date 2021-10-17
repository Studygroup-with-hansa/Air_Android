package com.hansarang.android.domain.usecase.subject

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.entity.dto.BaseSubject
import com.hansarang.android.domain.repository.SubjectRepository
import javax.inject.Inject

class GetSubjectUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
): BaseUseCase<BaseSubject>() {
    override suspend fun buildUseCaseSuspend(): BaseSubject {
        return subjectRepository.getSubject()
    }
}