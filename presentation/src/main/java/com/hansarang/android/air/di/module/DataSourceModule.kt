package com.hansarang.android.air.di.module

import com.hansarang.android.data.datasource.*
import com.hansarang.android.data.network.remote.*
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

    @Provides
    @Singleton
    fun providesWeeklyStatsDataSource(weeklyStatsRemote: WeeklyStatsRemote): WeeklyStatsDataSource =
        WeeklyStatsDataSource(weeklyStatsRemote)

    @Provides
    @Singleton
    fun providesSubjectDataSource(subjectRemote: SubjectRemote): SubjectDataSource =
        SubjectDataSource(subjectRemote)

    @Provides
    @Singleton
    fun providesCheckListDataSource(checkListRemote: CheckListRemote): CheckListDataSource =
        CheckListDataSource(checkListRemote)

    @Provides
    @Singleton
    fun providesGroupDataSource(groupRemote: GroupRemote): GroupDataSource =
        GroupDataSource(groupRemote)

    @Provides
    @Singleton
    fun providesTimerDataSource(timerRemote: TimerRemote): TimerDataSource =
        TimerDataSource(timerRemote)
}