package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.entity.dto.Subject
import com.hansarang.android.domain.usecase.subject.DeleteSubjectUseCase
import com.hansarang.android.domain.usecase.subject.GetSubjectByDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSubjectByDateUseCase: GetSubjectByDateUseCase,
    private val deleteSubjectUseCase: DeleteSubjectUseCase
) : ViewModel() {
    var isFirstLoad = true

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    val totalTime = MutableLiveData(0L)
    val goal = MutableLiveData(0L)

    private val _subjectList = MutableLiveData(ArrayList<Subject>())
    val subjectList: LiveData<ArrayList<Subject>> = _subjectList

    fun deleteSubject(subject: Subject) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = DeleteSubjectUseCase.Params(subject.title)
                    deleteSubjectUseCase.buildParamsUseCaseSuspend(params)
                    getSubjectList()
                }
            } catch (e: TimeoutCancellationException) {

            } catch (e: Throwable) {
                Log.d("TAG", "deleteSubject: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getSubjectList() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                delay(1000)
                withTimeout(10000) {
                    var totaltime = 0L
                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(System.currentTimeMillis()))
                    val params = GetSubjectByDateUseCase.Params(date)
                    val baseSubject = getSubjectByDateUseCase.buildParamsUseCaseSuspend(params)
                    val subjectList = ArrayList(baseSubject.subject)

                    _subjectList.value = subjectList
                    baseSubject.subject.forEach { totaltime += it.time }
                    totalTime.value = totaltime
                    goal.value = baseSubject.goal
                    _isEmpty.value = subjectList.isEmpty()
                }
            } catch (e: TimeoutCancellationException) {

            } catch (e: Throwable) {
                _isEmpty.value = true
            } finally {
                isFirstLoad = false
                _isLoading.value = false
            }
        }
    }
}