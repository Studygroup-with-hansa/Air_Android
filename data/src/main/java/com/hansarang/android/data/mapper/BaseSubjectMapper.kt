package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.BaseSubjectData
import com.hansarang.android.domain.entity.dto.BaseSubject

fun BaseSubjectData.toEntity(): BaseSubject {
    return BaseSubject(
        this.subject.map {
            it.toEntity()
        },
        this.goal
    )
}

fun BaseSubject.toData(): BaseSubjectData {
    return BaseSubjectData(
        this.subject.map {
            it.toData()
        },
        this.goal
    )
}