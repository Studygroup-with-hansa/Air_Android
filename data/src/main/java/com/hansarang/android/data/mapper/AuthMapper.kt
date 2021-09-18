package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.AuthData
import com.hansarang.android.domain.entity.dto.Auth

fun AuthData.toEntity(): Auth {
    return Auth(
        this.isEmailExist,
        this.emailSent
    )
}

fun Auth.toData(): AuthData {
    return AuthData(
        this.isEmailExist,
        this.emailSent
    )
}