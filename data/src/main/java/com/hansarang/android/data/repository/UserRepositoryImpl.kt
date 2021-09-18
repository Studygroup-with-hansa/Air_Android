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
    override suspend fun getRequestAuth(email: String): Auth {
        return userDataSource.getRequestAuth(email)
    }

    override suspend fun postSendAuthCode(email: String, auth: String): Token {
        return userDataSource.postSendAuthCode(email, auth)
    }

    override suspend fun putModifyUsername(name: String): String {
        return userDataSource.putModifyUsername(name)
    }

    override suspend fun putModifyEmail(email: String): String {
        return userDataSource.putModifyEmail(email)
    }

    override suspend fun getUserBasicInfo(email: String): User {
        return userDataSource.getUserBasicInfo(email)
    }
}