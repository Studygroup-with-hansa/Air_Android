package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.UserData
import com.hansarang.android.domain.entity.dto.User

fun UserData.toEntity(): User {
    return User(
        this.email,
        this.name
    )
}

fun User.toData(): UserData {
    return UserData(
        this.email,
        this.name
    )
}