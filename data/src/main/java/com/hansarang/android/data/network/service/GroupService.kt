package com.hansarang.android.data.network.service

import com.hansarang.android.data.entity.BaseGroupData
import com.hansarang.android.data.entity.BaseGroupDetailData
import com.hansarang.android.data.entity.GroupCodeData
import com.hansarang.android.domain.entity.response.BaseResponse
import retrofit2.http.*

interface GroupService {
    @GET("user/data/group")
    suspend fun getGroupList(): retrofit2.Response<BaseResponse<BaseGroupData>>

    @POST("user/data/group")
    suspend fun postCreateUserGroup(): retrofit2.Response<BaseResponse<GroupCodeData>>

    @FormUrlEncoded
    @POST("user/data/group/detail")
    suspend fun postViewGroupDetail(@Field("groupCode") groupCode: String): retrofit2.Response<BaseResponse<BaseGroupDetailData>>

    @PUT("user/data/group/detail/user")
    suspend fun putJoinGroup(@Query("groupCode") groupCode: String): retrofit2.Response<BaseResponse<GroupCodeData>>

    @DELETE("user/data/group/detail/user")
    suspend fun deleteGroupUser(
        @Query("userMail") userMail: String,
        @Query("groupCode") groupCode: String
    ): retrofit2.Response<BaseResponse<GroupCodeData>>
}