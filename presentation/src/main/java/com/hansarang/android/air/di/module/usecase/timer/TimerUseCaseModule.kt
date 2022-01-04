package com.hansarang.android.air.di.module.usecase.timer

import com.hansarang.android.domain.repository.TimerRepository
import com.hansarang.android.domain.usecase.timer.PostTimerStartUseCase
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TimerUseCaseModule {
    @Singleton
    @Provides
    fun providesPostTimerStartUseCase(timerRepository: TimerRepository) =
        PostTimerStartUseCase(timerRepository)

    @Singleton
    @Provides
    fun providesPostTimerStopUseCase(timerRepository: TimerRepository) =
        PostTimerStopUseCase(timerRepository)
}