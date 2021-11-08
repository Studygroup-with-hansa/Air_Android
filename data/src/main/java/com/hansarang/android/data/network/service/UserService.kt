package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.AuthData
import com.hansarang.android.data.entity.TokenData
import com.hansarang.android.data.entity.UserData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.*

interface UserService {

    // 인증 요청
    @FormUrlEncoded
    @POST("user/manage/signin/")
    suspend fun postRequestAuth(
        @Field("email") email: String
    ): retrofit2.Response<BaseResponse<AuthData>>

    // 인증 실행
    @PUT("user/manage/signin/")
    suspend fun putSendAuthCode(
        @Query("email") email: String,
        @Query("auth") auth: String
    ): retrofit2.Response<BaseResponse<TokenData>>

    // 유저 이름 수정
    @PUT("user/info/manage/basic/name/")
    suspend fun putModifyUsername(
        @Query("name") name: String
    ): retrofit2.Response<BaseResponse<Any?>>

    // 유저 이메일 수정
    @PUT("user/info/manage/basic/email/")
    suspend fun putModifyEmail(
        @Query("email") email: String
    ): retrofit2.Response<BaseResponse<Any?>>

    // 유저 기본 정보 받아오기
    @GET("user/info/manage/basic/")
    suspend fun getUserBasicInfo(): retrofit2.Response<BaseResponse<UserData>>

    @GET("user/info/manage/basic/checktoken/")
    suspend fun getCheckToken(): retrofit2.Response<BaseResponse<Any?>>

}