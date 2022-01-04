package com.hansarang.android.air.di.module.usecase.weeklystats

import com.hansarang.android.domain.repository.WeeklyStatsRepository
import com.hansarang.android.domain.usecase.weeklystats.GetWeeklyStatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object WeeklyStatsUseCaseModule {
    @Provides
    @Singleton
    fun providesGetWeeklyStatsUseCase(weeklyStatsRepository: WeeklyStatsRepository): GetWeeklyStatsUseCase =
        GetWeeklyStatsUseCase(weeklyStatsRepository)
}