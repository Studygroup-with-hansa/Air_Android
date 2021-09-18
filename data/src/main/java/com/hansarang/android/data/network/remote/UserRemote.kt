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

    suspend fun getRequestAuth(email: String): AuthData {
        return getResponse(service.getRequestAuth(email))
    }

    suspend fun postSendAuthCode(email: String, auth: String): TokenData {
        return getResponse(service.postSendAuthCode(email, auth))
    }

    suspend fun putModifyUsername(name: String): String {
        return getDetail(service.putModifyUsername(name))
    }

    suspend fun putModifyEmail(email: String): String {
        return getDetail(service.putModifyEmail(email))
    }

    suspend fun getUserBasicInfo(email: String): UserData {
        return getResponse(service.getUserBasicInfo(email))
    }

}