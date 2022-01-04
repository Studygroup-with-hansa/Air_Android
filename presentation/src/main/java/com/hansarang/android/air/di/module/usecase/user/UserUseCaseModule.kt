package com.hansarang.android.air.di.module.usecase.user

import com.hansarang.android.domain.repository.UserRepository
import com.hansarang.android.domain.usecase.user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserUseCaseModule {
    @Provides
    @Singleton
    fun providesGetRequestAuthUseCase(userRepository: UserRepository): PostRequestAuthUseCase =
        PostRequestAuthUseCase(userRepository)

    @Provides
    @Singleton
    fun providesPostSendAuthCodeUseCase(userRepository: UserRepository): PutSendAuthCodeUseCase =
        PutSendAuthCodeUseCase(userRepository)

    @Provides
    @Singleton
    fun providesPutModifyUsernameUseCase(userRepository: UserRepository): PutModifyUsernameUseCase =
        PutModifyUsernameUseCase(userRepository)

    @Provides
    @Singleton
    fun providesPutModifyEmailUseCase(userRepository: UserRepository): PutModifyEmailUseCase =
        PutModifyEmailUseCase(userRepository)

    @Provides
    @Singleton
    fun providesGetUserBasicInfoUseCase(userRepository: UserRepository): GetUserBasicInfoUseCase =
        GetUserBasicInfoUseCase(userRepository)

    @Provides
    @Singleton
    fun providesGetCheckToken(userRepository: UserRepository): GetCheckTokenUseCase =
        GetCheckTokenUseCase(userRepository)
}