package com.hansarang.android.domain.usecase.subject

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.SubjectRepository
import javax.inject.Inject

class PostSubjectUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
): BaseParamsUseCase<PostSubjectUseCase.Params, String>() {

    data class Params(
        val title: String,
        val color: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return subjectRepository.postSubject(params.title, params.color)
    }
}