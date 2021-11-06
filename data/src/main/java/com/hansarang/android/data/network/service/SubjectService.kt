package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.BaseSubjectData
import com.hansarang.android.data.entity.TargetTimeData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.*

interface SubjectService {

    // 오늘 데이터 가져오기
    @GET("user/data/subject/")
    suspend fun getSubject(): retrofit2.Response<BaseResponse<BaseSubjectData>>

    // 날짜에 따른 데이터 가져오기
    @FormUrlEncoded
    @POST("user/data/subject")
    suspend fun getSubjectByDate(@Field("date") date: String): retrofit2.Response<BaseResponse<BaseSubjectData>>

    // 새 과목 추가
    @FormUrlEncoded
    @POST("user/data/subject/manage/")
    suspend fun postSubject(
        @Field("title") title: String,
        @Field("color") color: String
    ): retrofit2.Response<BaseResponse<Any?>>

    // 과목 삭제
    @DELETE("user/data/subject/manage/")
    suspend fun deleteSubject(@Query("title") title: String): retrofit2.Response<BaseResponse<Any?>>

    // 과목 수정
    @PUT("user/data/subject/manage/")
    suspend fun putSubject(
        @Query("title") title: String,
        @Query("title_new") titleNew: String,
        @Query("color") color: String
    ): retrofit2.Response<BaseResponse<Any?>>

    @FormUrlEncoded
    @POST("user/data/subject/targettime/")
    suspend fun postTargetTime(
        @Field("targetTime") targetTime: Long
    ): retrofit2.Response<BaseResponse<Any?>>

    @GET("user/data/subject/targettime/")
    suspend fun getTargetTime(): retrofit2.Response<BaseResponse<TargetTimeData>>

}