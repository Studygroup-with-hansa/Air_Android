package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class PutModifyEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<PutModifyEmailUseCase.Params, String>() {
    data class Params(
        val email: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return userRepository.putModifyEmail(params.email)
    }
}