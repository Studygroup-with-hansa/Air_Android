package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.SingleLiveEvent
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

    var totalTime = MutableLiveData(0L)
    var goal = MutableLiveData(0L)
    var percents = MutableLiveData(0F)

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private val _subjectList = MutableLiveData(ArrayList<Subject>())
    val subjectList: LiveData<ArrayList<Subject>> get() = _subjectList

    private val _setTargetTimeButtonClick = SingleLiveEvent<Unit>()
    val setTargetTimeButtonClick: LiveData<Unit> get() = _setTargetTimeButtonClick

    private val _addSubjectButtonClick = SingleLiveEvent<Unit>()
    val addSubjectButtonClick: LiveData<Unit> get() = _addSubjectButtonClick

    fun onSetTargetTimeButtonClick() {
        _setTargetTimeButtonClick.call()
    }

    fun onAddSubjectButtonClick() {
        _addSubjectButtonClick.call()
    }

    fun deleteSubject(subject: Subject) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = DeleteSubjectUseCase.Params(subject.title)
                    deleteSubjectUseCase.buildParamsUseCaseSuspend(params)
                    val list = _subjectList.value
                    list?.remove(subject)
                    _subjectList.value = list
                    _isEmpty.value = list.isNullOrEmpty()

                    var totalTimeScope = 0L
                    list?.forEach { totalTimeScope += it.time }
                    percents.value = (totalTimeScope.toFloat() / goal.value!!.toFloat()) * 100f
                    totalTime.value = totalTimeScope
                }
            } catch (e: TimeoutCancellationException) {

            } catch (e: Throwable) {
                Log.d("TAG", "deleteSubject: ${e.message}")
            }
        }
    }

    fun getSubject() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                delay(1000)
                withTimeout(10000) {
                    var totalTimeScope = 0L
                    val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Date(System.currentTimeMillis()))
                    val params = GetSubjectByDateUseCase.Params(date)
                    val baseSubject = getSubjectByDateUseCase.buildParamsUseCaseSuspend(params)
                    val subjectList = ArrayList(baseSubject.subject)

                    _subjectList.value = subjectList
                    baseSubject.subject.forEach { totalTimeScope += it.time }
                    totalTime.value = totalTimeScope
                    percents.value = (totalTimeScope.toFloat() / baseSubject.goal.toFloat()) * 100f
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