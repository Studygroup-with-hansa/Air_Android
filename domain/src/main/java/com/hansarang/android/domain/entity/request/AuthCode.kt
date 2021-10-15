package com.hansarang.android.domain.entity.request

import retrofit2.http.Query

data class AuthCode(
    val email: String,
    val auth: String
)