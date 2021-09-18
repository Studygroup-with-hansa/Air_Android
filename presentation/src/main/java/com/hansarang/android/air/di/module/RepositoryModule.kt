package com.hansarang.android.air.di.module

import com.hansarang.android.data.datasource.UserDataSource
import com.hansarang.android.data.repository.UserRepositoryImpl
import com.hansarang.android.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun providesUserRepository(userDataSource: UserDataSource): UserRepository =
        UserRepositoryImpl(userDataSource)
}