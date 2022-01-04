package com.hansarang.android.data.network.service

import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TimerService {
    @FormUrlEncoded
    @POST("user/timer/start/")
    suspend fun postTimerStart(
        @Field("title") title: String
    ): retrofit2.Response<BaseResponse<Any?>>


    @FormUrlEncoded
    @POST("user/timer/stop/")
    suspend fun postTimerStop(
        @Field("title") title: String
    ): retrofit2.Response<BaseResponse<Any?>>
}