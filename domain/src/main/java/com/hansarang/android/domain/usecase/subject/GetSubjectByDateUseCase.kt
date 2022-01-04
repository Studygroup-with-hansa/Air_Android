package com.hansarang.android.domain.usecase.subject

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.entity.dto.BaseSubject
import com.hansarang.android.domain.repository.SubjectRepository
import javax.inject.Inject

class GetSubjectByDateUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
): BaseParamsUseCase<GetSubjectByDateUseCase.Params, BaseSubject>() {

    data class Params(val date: String)

    override suspend fun buildParamsUseCaseSuspend(params: Params): BaseSubject {
        return subjectRepository.getSubjectByDate(params.date)
    }

}