package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.Token
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class PutSendAuthCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<PutSendAuthCodeUseCase.Params, Token>() {

    data class Params(
        val email: String,
        val auth: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): Token {
        return userRepository.putSendAuthCode(params.email, params.auth)
    }

}