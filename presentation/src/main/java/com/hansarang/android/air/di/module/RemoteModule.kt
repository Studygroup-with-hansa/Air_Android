package com.hansarang.android.air.di.module

import com.hansarang.android.data.network.remote.UserRemote
import com.hansarang.android.data.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {
    @Provides
    @Singleton
    fun providesUserRemote(userService: UserService): UserRemote =
        UserRemote(userService)
}