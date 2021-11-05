package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.BaseTodoData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.*

interface CheckListService {
    @GET("user/data/subject/checklist/")
    suspend fun getTodoList(@Query("date") date: String): retrofit2.Response<BaseResponse<BaseTodoData>>

    @FormUrlEncoded
    @PUT("user/data/subject/checklist/")
    suspend fun putModifyCheckList(
        @Query("pk") pk: Int,
        @Field("todo") todo: String
    ): retrofit2.Response<BaseResponse<Any?>>

    @FormUrlEncoded
    @POST("user/data/subject/checklist/")
    suspend fun postCheckList(
        @Field("subject") subject: String,
        @Field("date") date: String,
        @Field("todo") todo: String
    ): retrofit2.Response<BaseResponse<Any?>>

    @PUT("user/data/subject/checklist/status")
    suspend fun putStatusChangeCheckList(
        @Query("pk") pk: Int
    ): retrofit2.Response<BaseResponse<Any?>>

    @FormUrlEncoded
    @POST("user/data/subject/checklist/memo")
    suspend fun postMemoTodoList(
        @Field("date") date: String,
        @Field("memo") memo: String
    ): retrofit2.Response<BaseResponse<Any?>>
}