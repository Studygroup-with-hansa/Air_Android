package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.BaseTodoData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CheckListService {
    @FormUrlEncoded
    @POST("user/data/subject/checklist/")
    suspend fun getCheckList(@Field("date") date: String): retrofit2.Response<BaseResponse<BaseTodoData>>
}