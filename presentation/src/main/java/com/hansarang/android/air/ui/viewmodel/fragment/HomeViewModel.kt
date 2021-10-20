package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
import android.view.View
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
    private val deleteSubjectUseCase: DeleteSubjectUseCase
) : ViewModel() {
    val progressBarVisibility = MutableLiveData(View.GONE)

    val goal = MutableLiveData(0L)

    private val _subjectList = MutableLiveData(ArrayList<Subject>())
    val subjectList: LiveData<ArrayList<Subject>> = _subjectList

    fun deleteSubject(subject: Subject) {
        progressBarVisibility.value = View.VISIBLE

        viewModelScope.launch {
            try {
                val params = DeleteSubjectUseCase.Params(subject.title)
                deleteSubjectUseCase.buildParamsUseCaseSuspend(params)
                getSubjectList()
            } catch (e: Throwable) {
                Log.d("TAG", "deleteSubject: ${e.message}")
            } finally {
                progressBarVisibility.value = View.GONE
            }
        }
    }

    fun getSubjectList() {
        progressBarVisibility.value = View.VISIBLE

        viewModelScope.launch {
            val baseSubject = getSubjectUseCase.buildUseCaseSuspend()
            _subjectList.value = ArrayList(baseSubject.subject)
            goal.value = baseSubject.goal
            progressBarVisibility.value = View.GONE
        }
    }
}