package com.hansarang.android.air.di.module

import com.hansarang.android.data.datasource.CheckListDataSource
import com.hansarang.android.data.datasource.SubjectDataSource
import com.hansarang.android.data.datasource.UserDataSource
import com.hansarang.android.data.datasource.WeeklyStatsDataSource
import com.hansarang.android.data.repository.CheckListRepositoryImpl
import com.hansarang.android.data.repository.SubjectRepositoryImpl
import com.hansarang.android.data.repository.UserRepositoryImpl
import com.hansarang.android.data.repository.WeeklyStatsRepositoryImpl
import com.hansarang.android.domain.repository.CheckListRepository
import com.hansarang.android.domain.repository.SubjectRepository
import com.hansarang.android.domain.repository.UserRepository
import com.hansarang.android.domain.repository.WeeklyStatsRepository
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
}