package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.entity.dto.Subject
import com.hansarang.android.domain.usecase.subject.DeleteSubjectUseCase
import com.hansarang.android.domain.usecase.subject.GetSubjectUseCase
import com.hansarang.android.domain.usecase.subject.PutSubjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSubjectUseCase: GetSubjectUseCase,
    private val deleteSubjectUseCase: DeleteSubjectUseCase,
    private val putSubjectUseCase: PutSubjectUseCase
) : ViewModel() {
    val goal = MutableLiveData(0L)

    private val _subjectList = MutableLiveData(ArrayList<Subject>())
    val subjectList: LiveData<ArrayList<Subject>> = _subjectList

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            val params = DeleteSubjectUseCase.Params(subject.title)
            deleteSubjectUseCase.buildParamsUseCaseSuspend(params)
        }
    }

    fun modifySubject(oldSubject: Subject, newSubject: Subject) {
        viewModelScope.launch {
            val params = PutSubjectUseCase.Params(oldSubject.title, newSubject.title, newSubject.color)
            putSubjectUseCase.buildParamsUseCaseSuspend(params)
        }
    }

    fun getSubjectList() {
        viewModelScope.launch {
            val baseSubject = getSubjectUseCase.buildUseCaseSuspend()
            _subjectList.value = ArrayList(baseSubject.subject)
            goal.value = baseSubject.goal
        }
    }
}