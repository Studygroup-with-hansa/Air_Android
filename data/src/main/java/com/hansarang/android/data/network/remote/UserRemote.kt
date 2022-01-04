package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.entity.AuthData
import com.hansarang.android.data.entity.TokenData
import com.hansarang.android.data.entity.UserData
import com.hansarang.android.data.network.service.UserService
import javax.inject.Inject

class UserRemote @Inject constructor(
    override val service: UserService
): BaseRemote<UserService>() {

    suspend fun postRequestAuth(email: String): AuthData {
        return getResponse(service.postRequestAuth(email))
    }

    suspend fun putSendAuthCode(email: String, auth: String): TokenData {
        return getResponse(service.putSendAuthCode(email, auth))
    }

    suspend fun putModifyUsername(name: String): String {
        return getDetail(service.putModifyUsername(name))
    }

    suspend fun putModifyEmail(email: String): String {
        return getDetail(service.putModifyEmail(email))
    }

    suspend fun getUserBasicInfo(): UserData {
        return getResponse(service.getUserBasicInfo())
    }

    suspend fun getCheckToken(): String {
        return getDetail(service.getCheckToken())
    }

}