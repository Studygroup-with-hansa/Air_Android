package com.hansarang.android.air.di.module

import com.hansarang.android.data.datasource.UserDataSource
import com.hansarang.android.data.network.remote.UserRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Provides
    @Singleton
    fun providesUserDataSource(userRemote: UserRemote): UserDataSource =
        UserDataSource(userRemote)
}