package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.Auth
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class GetRequestAuthUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<GetRequestAuthUseCase.Params, Auth>() {

    data class Params(
        val email: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): Auth {
        return userRepository.getRequestAuth(params.email)
    }

}