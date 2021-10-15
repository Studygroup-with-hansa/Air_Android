package com.hansarang.android.air.ui.interceptor

import com.hansarang.android.air.ui.util.SharedPreferenceHelper.token
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", token)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        return chain.proceed(request)
    }
}