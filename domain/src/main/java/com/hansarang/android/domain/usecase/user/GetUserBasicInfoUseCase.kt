package com.hansarang.android.domain.usecase.user

import com.hansarang.android.domain.base.BaseUseCase
import com.hansarang.android.domain.entity.dto.User
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class GetUserBasicInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
): BaseUseCase<User>() {

    override suspend fun buildUseCaseSuspend(): User {
        return userRepository.getUserBasicInfo()
    }
}