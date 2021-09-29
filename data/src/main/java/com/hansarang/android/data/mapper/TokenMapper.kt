package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.TokenData
import com.hansarang.android.domain.entity.dto.Token

fun TokenData.toEntity(): Token {
    return Token(
        this.token
    )
}

fun Token.toData(): TokenData {
    return TokenData(
        this.token
    )
}