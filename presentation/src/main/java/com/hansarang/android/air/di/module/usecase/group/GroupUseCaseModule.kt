package com.hansarang.android.air.di.module.usecase.group

import com.hansarang.android.domain.repository.GroupRepository
import com.hansarang.android.domain.usecase.group.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object GroupUseCaseModule {
    @Provides
    @Singleton
    fun providesGetGroupListUseCase(groupRepository: GroupRepository) =
        GetGroupListUseCase(groupRepository)

    @Provides
    @Singleton
    fun providesPostCreateUserGroupUseCase(groupRepository: GroupRepository) =
        PostCreateUserGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun providesPostViewGroupDetail(groupRepository: GroupRepository) =
        PostViewGroupDetail(groupRepository)

    @Provides
    @Singleton
    fun providesPutJoinGroupUseCase(groupRepository: GroupRepository) =
        PutJoinGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun providesDeleteGroupUseCase(groupRepository: GroupRepository) =
        DeleteGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun providesDeleteLeaveGroupUseCase(groupRepository: GroupRepository) =
        DeleteLeaveGroupUseCase(groupRepository)

    @Provides
    @Singleton
    fun providesDeleteGroupUserUseCase(groupRepository: GroupRepository) =
        DeleteGroupUserUseCase(groupRepository)
}