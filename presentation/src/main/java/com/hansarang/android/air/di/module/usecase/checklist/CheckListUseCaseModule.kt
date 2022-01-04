package com.hansarang.android.air.di.module.usecase.checklist

import com.hansarang.android.domain.repository.CheckListRepository
import com.hansarang.android.domain.usecase.checklist.*
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
        GetTodoListUseCase(checkListRepository)

    @Provides
    @Singleton
    fun providesPutModifyCheckListUseCase(checkListRepository: CheckListRepository) =
        PutModifyCheckListUseCase(checkListRepository)

    @Provides
    @Singleton
    fun providesPostCheckListUseCase(checkListRepository: CheckListRepository) =
        PostCheckListUseCase(checkListRepository)

    @Provides
    @Singleton
    fun providesPutStatusChangeCheckListUseCase(checkListRepository: CheckListRepository) =
        PutStatusChangeCheckListUseCase(checkListRepository)

    @Provides
    @Singleton
    fun providesDeleteCheckListUseCase(checkListRepository: CheckListRepository) =
        DeleteCheckListUseCase(checkListRepository)

    @Provides
    @Singleton
    fun providesPostMemoTodoListUseCase(checkListRepository: CheckListRepository) =
        PostMemoTodoListUseCase(checkListRepository)
}