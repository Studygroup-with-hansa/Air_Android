package com.hansarang.android.air.di.module

import com.hansarang.android.data.network.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun providesWeeklyStatsService(retrofit: Retrofit): WeeklyStatsService =
        retrofit.create(WeeklyStatsService::class.java)

    @Provides
    @Singleton
    fun providesSubjectService(retrofit: Retrofit): SubjectService =
        retrofit.create(SubjectService::class.java)

    @Provides
    @Singleton
    fun providesCheckListService(retrofit: Retrofit): CheckListService =
        retrofit.create(CheckListService::class.java)

    @Provides
    @Singleton
    fun providesGroupService(retrofit: Retrofit): GroupService =
        retrofit.create(GroupService::class.java)
}