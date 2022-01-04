package com.hansarang.android.domain.repository

import com.hansarang.android.domain.entity.dto.BaseSubject
import com.hansarang.android.domain.entity.dto.TargetTime

interface SubjectRepository {
    suspend fun getSubject(): BaseSubject
    suspend fun getSubjectByDate(date: String): BaseSubject
    suspend fun postSubject(title: String, color: String): String
    suspend fun deleteSubject(title: String): String
    suspend fun putSubject(title: String, titleNew: String, color: String): String
    suspend fun postTargetTime(targetTime: Long): String
    suspend fun getTargetTime(): TargetTime
}