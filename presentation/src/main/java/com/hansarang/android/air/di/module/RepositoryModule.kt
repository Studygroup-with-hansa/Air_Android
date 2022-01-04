package com.hansarang.android.air.di.module

import com.hansarang.android.data.datasource.*
import com.hansarang.android.data.repository.*
import com.hansarang.android.domain.repository.*
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

    @Provides
    @Singleton
    fun providesWeeklyStatsRepository(weeklyStatsDataSource: WeeklyStatsDataSource): WeeklyStatsRepository =
        WeeklyStatsRepositoryImpl(weeklyStatsDataSource)

    @Provides
    @Singleton
    fun providesSubjectRepository(subjectDataSource: SubjectDataSource): SubjectRepository =
        SubjectRepositoryImpl(subjectDataSource)

    @Provides
    @Singleton
    fun providesCheckListRepository(checkListDataSource: CheckListDataSource): CheckListRepository =
        CheckListRepositoryImpl(checkListDataSource)

    @Provides
    @Singleton
    fun providesGroupRepository(groupDataSource: GroupDataSource): GroupRepository =
        GroupRepositoryImpl(groupDataSource)

    @Provides
    @Singleton
    fun providesTimerRepository(timerDataSource: TimerDataSource): TimerRepository =
        TimerRepositoryImpl(timerDataSource)
}