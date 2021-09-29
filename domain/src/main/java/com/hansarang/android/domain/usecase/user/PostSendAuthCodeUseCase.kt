package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.Token
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class PostSendAuthCodeUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<PostSendAuthCodeUseCase.Params, Token>() {

    data class Params(
        val email: String,
        val auth: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): Token {
        return userRepository.postSendAuthCode(params.email, params.auth)
    }

}