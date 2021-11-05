package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.Todo
import com.hansarang.android.domain.usecase.checklist.GetTodoListUseCase
import com.hansarang.android.domain.usecase.checklist.PostCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutModifyCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutStatusChangeCheckList
import com.hansarang.android.domain.usecase.subject.GetSubjectUseCase
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
class TodoViewModel @Inject constructor(
    private val getSubjectUseCase: GetSubjectUseCase,
    private val getTodoListUseCase: GetTodoListUseCase,
    private val postCheckListUseCase: PostCheckListUseCase,
    private val putModifyCheckListUseCase: PutModifyCheckListUseCase,
    private val putStatusChangeCheckList: PutStatusChangeCheckList
): ViewModel() {
    var isFirstLoad = true

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val memo = MutableLiveData<String>()

    private val _date = MutableLiveData(System.currentTimeMillis())
    val date: LiveData<Long> = _date

    private val _todoList = MutableLiveData<ArrayList<Todo>>()
    val todoList: LiveData<ArrayList<Todo>> = _todoList

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun setDay(currentYMD: String, amount: Int) {
        val ymdArray = currentYMD.split(".").map { it.toInt() }
        val calendar = Calendar.Builder()
            .setDate(ymdArray[0], ymdArray[1] - 1, ymdArray[2])
            .build()
        calendar.add(Calendar.DAY_OF_MONTH, amount)
        _date.value = calendar.timeInMillis
        getTodos()
    }

    fun getTodos() {
        _isLoading.value = true
        val date = _date.value ?: 0L
        viewModelScope.launch {
            try {
                delay(1000)
                withTimeout(10000) {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                    val params = GetTodoListUseCase.Params(sdf.format(date))
                    val curSubject = getSubjectUseCase.buildUseCaseSuspend().subject.map {
                        it.title
                    }
                    getTodoListUseCase.buildParamsUseCaseSuspend(params).apply {
                        this@TodoViewModel.memo.value = this.memo
                        val todoList = ArrayList<Todo>()
                        this.subjects.forEach() {
                            if (curSubject.contains(it.subject)) todoList.add(it)
                        }
                        _todoList.value = todoList
                        _isEmpty.value = todoList.isEmpty()
                    }
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isEmpty.value = true
                _isFailure.value = Event(e.message ?: "")
            } finally {
                isFirstLoad = false
                _isLoading.value = false
            }

        }
    }

    fun postCheckList(subject: String, date: String, todo: String) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = PostCheckListUseCase.Params(subject, date, todo)
                    postCheckListUseCase.buildParamsUseCaseSuspend(params)
                    getTodos()
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            }
        }
    }

    fun deleteCheckList(pk: Int, todo: String) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {}
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            } finally {
                getTodos()
            }
        }
    }

    fun putCheckList(pk: Int, todo: String) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = PutModifyCheckListUseCase.Params(pk, todo)
                    putModifyCheckListUseCase.buildParamsUseCaseSuspend(params)
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            } finally {
                getTodos()
            }
        }
    }

    fun putStatusChangeCheckList(pk: Int) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = PutStatusChangeCheckList.Params(pk)
                    putStatusChangeCheckList.buildParamsUseCaseSuspend(params)
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            }
        }
    }
}