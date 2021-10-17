package com.hansarang.android.data.network.remote

import com.hansarang.android.data.base.BaseRemote
import com.hansarang.android.data.entity.BaseSubjectData
import com.hansarang.android.data.network.service.SubjectService
import javax.inject.Inject

class SubjectRemote @Inject constructor(
    override val service: SubjectService
): BaseRemote<SubjectService>() {

    suspend fun getSubject(): BaseSubjectData {
        return getResponse(service.getSubject())
    }

    suspend fun getSubjectByDate(date: String): BaseSubjectData {
        return getResponse(service.getSubjectByDate(date))
    }

    suspend fun postSubject(
        title: String,
        color: String
    ): String {
        return getDetail(service.postSubject(title, color))
    }

    suspend fun deleteSubject(title: String): String {
        return getDetail(service.deleteSubject(title))
    }

    suspend fun putSubject(
        title: String,
        titleNew: String,
        color: String
    ): String {
        return getDetail(service.putSubject(title, titleNew, color))
    }

}