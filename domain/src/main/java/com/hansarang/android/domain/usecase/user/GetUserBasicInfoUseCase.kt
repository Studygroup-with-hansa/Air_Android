package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.User
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class GetUserBasicInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<GetUserBasicInfoUseCase.Params, User>() {
    data class Params(
        val email: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): User {
        return userRepository.getUserBasicInfo(params.email)
    }
}