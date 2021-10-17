package com.hansarang.android.air.di.module.usecase.subject

import com.hansarang.android.domain.repository.SubjectRepository
import com.hansarang.android.domain.usecase.subject.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SubjectUseCaseModule {
    @Provides
    @Singleton
    fun providesGetSubjectUseCase(subjectRepository: SubjectRepository): GetSubjectUseCase =
        GetSubjectUseCase(subjectRepository)

    @Provides
    @Singleton
    fun providesGetSubjectByDateUseCase(subjectRepository: SubjectRepository): GetSubjectByDateUseCase =
        GetSubjectByDateUseCase(subjectRepository)

    @Provides
    @Singleton
    fun providesPostSubjectUseCase(subjectRepository: SubjectRepository): PostSubjectUseCase =
        PostSubjectUseCase(subjectRepository)

    @Provides
    @Singleton
    fun providesDeleteSubjectUseCase(subjectRepository: SubjectRepository): DeleteSubjectUseCase =
        DeleteSubjectUseCase(subjectRepository)

    @Provides
    @Singleton
    fun providesPutSubjectUseCase(subjectRepository: SubjectRepository): PutSubjectUseCase =
        PutSubjectUseCase(subjectRepository)
}