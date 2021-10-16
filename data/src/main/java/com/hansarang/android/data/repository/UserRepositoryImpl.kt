package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.UserDataSource
import com.hansarang.android.domain.entity.dto.Auth
import com.hansarang.android.domain.entity.dto.Token
import com.hansarang.android.domain.entity.dto.User
import com.hansarang.android.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
): UserRepository {
    override suspend fun postRequestAuth(email: String): Auth {
        return userDataSource.postRequestAuth(email)
    }

    override suspend fun putSendAuthCode(email: String, auth: String): Token {
        return userDataSource.putSendAuthCode(email, auth)
    }

    override suspend fun putModifyUsername(name: String): String {
        return userDataSource.putModifyUsername(name)
    }

    override suspend fun putModifyEmail(email: String): String {
        return userDataSource.putModifyEmail(email)
    }

    override suspend fun getUserBasicInfo(): User {
        return userDataSource.getUserBasicInfo()
    }
}