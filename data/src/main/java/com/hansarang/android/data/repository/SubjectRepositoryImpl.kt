package com.hansarang.android.data.repository

import com.hansarang.android.data.datasource.SubjectDataSource
import com.hansarang.android.domain.entity.dto.BaseSubject
import com.hansarang.android.domain.repository.SubjectRepository
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectDataSource: SubjectDataSource
): SubjectRepository {
    override suspend fun getSubject(): BaseSubject {
        return subjectDataSource.getSubject()
    }

    override suspend fun getSubjectByDate(date: String): BaseSubject {
        return subjectDataSource.getSubjectByDate(date)
    }

    override suspend fun postSubject(title: String, color: String): String {
        return subjectDataSource.postSubject(title, color)
    }

    override suspend fun deleteSubject(title: String): String {
        return subjectDataSource.deleteSubject(title)
    }

    override suspend fun putSubject(title: String, titleNew: String, color: String): String {
        return subjectDataSource.putSubject(title, titleNew, color)
    }

    override suspend fun postTargetTime(targetTime: String): String {
        return subjectDataSource.postTargetTime(targetTime)
    }
}