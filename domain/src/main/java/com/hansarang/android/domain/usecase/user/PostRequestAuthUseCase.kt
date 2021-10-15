package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.Auth
import com.hansarang.android.domain.entity.request.Email
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class PostRequestAuthUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<PostRequestAuthUseCase.Params, Auth>() {

    data class Params(
        val email: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): Auth {
        return userRepository.postRequestAuth(params.email)
    }

}