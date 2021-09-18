package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.AuthData
import com.hansarang.android.data.entity.TokenData
import com.hansarang.android.data.entity.UserData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {

    // 인증 요청
    @GET("/user/manage/signin/")
    suspend fun getRequestAuth(
        @Query("email") email: String
    ): retrofit2.Response<BaseResponse<AuthData>>

    // 인증 실행
    @POST("/user/manage/signin/")
    suspend fun postSendAuthCode(
        @Query("email") email: String,
        @Query("auth") auth: String
    ): retrofit2.Response<BaseResponse<TokenData>>

    // 유저 이름 수정
    @PUT("/user/info/manage/basic/name/")
    suspend fun putModifyUsername(
        @Query("name") name: String
    ): retrofit2.Response<BaseResponse<Any?>>

    // 유저 이메일 수정
    @PUT("/user/info/manage/basic/email/")
    suspend fun putModifyEmail(
        @Query("email") email: String
    ): retrofit2.Response<BaseResponse<Any?>>

    // 유저 이메일 수정
    @GET("/user/info/basic/")
    suspend fun getUserBasicInfo(
        @Query("email") email: String
    ): retrofit2.Response<BaseResponse<UserData>>

}