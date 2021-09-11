package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.Subject

class HomeViewModel: ViewModel() {
    private val _subjectList = MutableLiveData(ArrayList<Subject>())
    val subjectList: LiveData<ArrayList<Subject>> = _subjectList

    fun deleteSubject(subject: Subject) {

    }

    fun modifySubject(subject: Subject) {

    }

    fun getSubjectList() {
        _subjectList.value = arrayListOf(Subject("국어", 10, "#ED6A5E"))
    }
}