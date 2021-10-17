package com.hansarang.android.domain.usecase.subject

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.SubjectRepository
import javax.inject.Inject

class DeleteSubjectUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
): BaseParamsUseCase<DeleteSubjectUseCase.Params, String>() {
    data class Params(val title: String)

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return subjectRepository.deleteSubject(params.title
        )
    }
}