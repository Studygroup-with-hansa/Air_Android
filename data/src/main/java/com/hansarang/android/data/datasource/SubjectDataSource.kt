package com.hansarang.android.data.datasource

import com.hansarang.android.data.base.BaseDataSource
import com.hansarang.android.data.mapper.toEntity
import com.hansarang.android.data.network.remote.SubjectRemote
import com.hansarang.android.domain.entity.dto.BaseSubject
import javax.inject.Inject

class SubjectDataSource @Inject constructor(
    override val remote: SubjectRemote
): BaseDataSource<SubjectRemote>() {

    suspend fun getSubject(): BaseSubject {
        return remote.getSubject().toEntity()
    }

    suspend fun getSubjectByDate(date: String): BaseSubject {
        return remote.getSubjectByDate(date).toEntity()
    }

    suspend fun postSubject(
        title: String,
        color: String
    ): String {
        return remote.postSubject(title, color)
    }

    suspend fun deleteSubject(title: String): String {
        return remote.deleteSubject(title)
    }

    suspend fun putSubject(
        title: String,
        titleNew: String,
        color: String
    ): String {
        return remote.putSubject(title, titleNew, color)
    }

}