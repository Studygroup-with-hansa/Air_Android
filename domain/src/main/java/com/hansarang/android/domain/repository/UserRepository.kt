package com.hansarang.android.domain.repository

import com.hansarang.android.domain.entity.dto.Auth
import com.hansarang.android.domain.entity.dto.Token
import com.hansarang.android.domain.entity.dto.User

interface UserRepository {
    suspend fun getRequestAuth(email: String): Auth
    suspend fun postSendAuthCode(email: String, auth: String): Token
    suspend fun putModifyUsername(name: String): String
    suspend fun putModifyEmail(email: String): String
    suspend fun getUserBasicInfo(email: String): User
}