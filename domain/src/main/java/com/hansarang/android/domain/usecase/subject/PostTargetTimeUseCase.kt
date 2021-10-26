package com.hansarang.android.domain.usecase.subject

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.SubjectRepository
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class PostTargetTimeUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
): BaseParamsUseCase<PostTargetTimeUseCase.Params, String>() {
    data class Params(
        val targetTime: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return subjectRepository.postTargetTime(params.targetTime)
    }
}