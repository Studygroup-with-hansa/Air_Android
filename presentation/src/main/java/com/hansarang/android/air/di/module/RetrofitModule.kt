package com.hansarang.android.air.di.module

import com.hansarang.android.air.ui.interceptor.TokenInterceptor
import com.hansarang.android.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    @Singleton
    fun providesOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor())
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.TEST_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
}