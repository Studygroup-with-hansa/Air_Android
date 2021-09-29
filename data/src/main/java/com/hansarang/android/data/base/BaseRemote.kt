package com.hansarang.android.data.base

import com.google.gson.Gson
import com.hansarang.android.domain.entity.response.BaseResponse

abstract class BaseRemote<SV> {
    protected abstract val service: SV

    fun <T> getResponse(response: retrofit2.Response<BaseResponse<T>>): T {
        checkError(response)
        return response.body()!!.data
    }

    fun <T> getDetail(response: retrofit2.Response<BaseResponse<T>>): String {
        checkError(response)
        return response.body()!!.detail
    }

    private fun <T> checkError(response: retrofit2.Response<BaseResponse<T>>) {
        if (!response.isSuccessful) {
            val gson = Gson()
            val errorBody = gson.fromJson(response.errorBody()!!.charStream(), BaseResponse::class.java)
            throw Throwable(errorBody.status.toString())
        }
    }
}