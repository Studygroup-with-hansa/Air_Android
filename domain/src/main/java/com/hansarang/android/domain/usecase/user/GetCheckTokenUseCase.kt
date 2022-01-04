package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class GetCheckTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<String>() {
    override suspend fun buildUseCaseSuspend(): String {
        return userRepository.getCheckToken()
    }
}