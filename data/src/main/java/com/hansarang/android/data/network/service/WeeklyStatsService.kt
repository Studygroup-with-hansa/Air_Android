package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.BaseStatsData
import com.hansarang.android.data.entity.StatsData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WeeklyStatsService {

    // startDate-endDate 사이의 통계 데이터 가져오기
    @FormUrlEncoded
    @POST("user/data/stats/")
    suspend fun getStats(
        @Field("startDate") startDate: String,
        @Field("endDate") endDate: String
    ): retrofit2.Response<BaseResponse<BaseStatsData>>
}