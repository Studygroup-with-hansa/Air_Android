package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class PutModifyUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseParamsUseCase<PutModifyUsernameUseCase.Params, String>() {
    data class Params(
        val name: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return userRepository.putModifyUsername(params.name)
    }
}