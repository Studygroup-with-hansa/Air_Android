package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.Todo
import com.hansarang.android.domain.usecase.checklist.DeleteCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.GetTodoListUseCase
import com.hansarang.android.domain.usecase.checklist.PostMemoTodoListUseCase
import com.hansarang.android.domain.usecase.subject.GetSubjectByDateUseCase
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
    private val getSubjectByDateUseCase: GetSubjectByDateUseCase,
    private val getTodoListUseCase: GetTodoListUseCase,
    private val postMemoTodoListUseCase: PostMemoTodoListUseCase,
): ViewModel() {
    var isFirstLoad = true

    private val _isNextButtonEnabled = MutableLiveData<Boolean>()
    val isNextEnabled: LiveData<Boolean> = _isNextButtonEnabled

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isSuccess = MutableLiveData<ArrayList<Todo>>()
    val isSuccess: LiveData<ArrayList<Todo>> = _isSuccess

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> = _isEmpty

    private val _date = MutableLiveData(System.currentTimeMillis())
    val date: LiveData<Long> = _date

    val memo = MutableLiveData<String>()

    fun setDay(currentYMD: String, amount: Int) {
        val ymdArray = currentYMD.split("-").map { it.toInt() }
        val calendar = Calendar.Builder()
            .setDate(ymdArray[0], ymdArray[1] - 1, ymdArray[2])
            .build()
        calendar.add(Calendar.DAY_OF_MONTH, amount)

        val todayTimeInMillis = calendar.timeInMillis
        _date.value = todayTimeInMillis

        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val currentTime = sdf.format(Date(System.currentTimeMillis()))
        val textTodayTime = sdf.format(Date(todayTimeInMillis))
        _isNextButtonEnabled.value = currentTime != textTodayTime

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
                    val dateFormat = sdf.format(date)
                    val params = GetTodoListUseCase.Params(dateFormat)
                    getTodoListUseCase.buildParamsUseCaseSuspend(params).apply {
                        this@TodoViewModel.memo.value = this.memo
                        val todoList = ArrayList(this.subjects)
                        _isSuccess.value =
                            if (todoList.isNotEmpty()) {
                                _isEmpty.value = todoList.isEmpty()
                                todoList
                            }
                            else {
                                val subjectParams = GetSubjectByDateUseCase.Params(dateFormat)
                                val newTodoList = getSubjectByDateUseCase.buildParamsUseCaseSuspend(
                                    subjectParams
                                ).subject.map { Todo(it.title) }
                                _isEmpty.value = newTodoList.isEmpty()
                                ArrayList(newTodoList)
                            }
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

    fun postMemo() {
        val date = _date.value ?: 0L
        val memo = memo.value ?: ""

        viewModelScope.launch {
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
                val params = PostMemoTodoListUseCase.Params(sdf.format(date), memo)
                postMemoTodoListUseCase.buildParamsUseCaseSuspend(params)
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            }
        }
    }
}