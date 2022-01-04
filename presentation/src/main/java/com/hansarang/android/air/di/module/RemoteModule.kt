package com.hansarang.android.air.di.module

import com.hansarang.android.data.network.remote.*
import com.hansarang.android.data.network.service.*
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

    @Provides
    @Singleton
    fun providesWeeklyStatsRemote(weeklyStatsService: WeeklyStatsService): WeeklyStatsRemote =
        WeeklyStatsRemote(weeklyStatsService)

    @Provides
    @Singleton
    fun providesSubjectRemote(subjectService: SubjectService): SubjectRemote =
        SubjectRemote(subjectService)

    @Provides
    @Singleton
    fun providesCheckListRemote(checkListService: CheckListService): CheckListRemote =
        CheckListRemote(checkListService)

    @Provides
    @Singleton
    fun providesGroupRemote(groupService: GroupService): GroupRemote =
        GroupRemote(groupService)

    @Provides
    @Singleton
    fun providesTimerRemote(timerService: TimerService): TimerRemote =
        TimerRemote(timerService)
}