package com.hansarang.android.data.mapper

import com.hansarang.android.data.entity.SubjectData
import com.hansarang.android.domain.entity.dto.Subject

fun SubjectData.toEntity(): Subject {
    return Subject(
        this.title,
        this.time,
        this.color,
    )
}

fun Subject.toData(): SubjectData {
    return SubjectData(
        this.title,
        this.time,
        this.color,
    )
}