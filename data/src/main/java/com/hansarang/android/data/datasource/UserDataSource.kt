package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.mapper.toEntity
import com.hansarang.android.data.network.remote.UserRemote
import com.hansarang.android.domain.entity.dto.Auth
import com.hansarang.android.domain.entity.dto.Token
import com.hansarang.android.domain.entity.dto.User
import javax.inject.Inject

class UserDataSource @Inject constructor(
    override val remote: UserRemote
): BaseDataSource<UserRemote>() {

    suspend fun postRequestAuth(email: String): Auth {
        return remote.postRequestAuth(email).toEntity()
    }

    suspend fun putSendAuthCode(email: String, auth: String): Token {
        return remote.putSendAuthCode(email, auth).toEntity()
    }

    suspend fun putModifyUsername(name: String): String {
        return remote.putModifyUsername(name)
    }

    suspend fun putModifyEmail(email: String): String {
        return remote.putModifyEmail(email)
    }

    suspend fun getUserBasicInfo(): User {
        return remote.getUserBasicInfo().toEntity()
    }

}