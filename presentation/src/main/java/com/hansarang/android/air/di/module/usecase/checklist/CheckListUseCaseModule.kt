package com.hansarang.android.air.di.module.usecase.checklist

import com.hansarang.android.domain.repository.CheckListRepository
import com.hansarang.android.domain.usecase.checklist.GetCheckListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CheckListUseCaseModule {
    @Provides
    @Singleton
    fun providesGetCheckListUseCase(checkListRepository: CheckListRepository) =
        GetCheckListUseCase(checkListRepository)
}