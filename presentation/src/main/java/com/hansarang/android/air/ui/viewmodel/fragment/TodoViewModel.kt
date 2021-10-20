package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.entity.dto.Todo
import com.hansarang.android.domain.entity.dto.TodoListItem
import com.hansarang.android.domain.usecase.checklist.GetCheckListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getCheckListUseCase: GetCheckListUseCase
): ViewModel() {
    val progressBarVisibility = MutableLiveData(View.GONE)

    val memo = MutableLiveData<String>()

    private val _date = MutableLiveData(System.currentTimeMillis())
    val date: LiveData<Long> = _date

    private val _todoList = MutableLiveData<ArrayList<Todo>>()
    val todoList: LiveData<ArrayList<Todo>> = _todoList

    fun setDay(currentYMD: String, amount: Int) {
        val ymdArray = currentYMD.split(".").map { it.toInt() }
        val calendar = Calendar.Builder()
            .setDate(ymdArray[0], ymdArray[1] - 1, ymdArray[2])
            .build()
        calendar.add(Calendar.DAY_OF_MONTH, amount)
        _date.value = calendar.timeInMillis
        getTodoList()
    }

    fun getTodoList() {
        progressBarVisibility.value = View.VISIBLE
        val date = _date.value ?: 0L
        viewModelScope.launch {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val params = GetCheckListUseCase.Params(sdf.format(date))
            val baseTodo = getCheckListUseCase.buildParamsUseCaseSuspend(params)
            _todoList.value = if (sdf.format(date) == "2021-10-19") {
                memo.value = "메모"
                arrayListOf(
                    Todo("국어", arrayListOf(TodoListItem(true, "뭐할까요?"), TodoListItem(true, "ㅇ?"))),
                    Todo("수학", arrayListOf(TodoListItem(true, "뭐할까요?"), TodoListItem(true, "ㅇ?")))
                )
            } else {
                memo.value = baseTodo.memo
                ArrayList(baseTodo.subjects)
            }
            progressBarVisibility.value = View.GONE
        }
    }

    fun postCheckList(title: String, value: String) {

    }

    fun deleteCheckList(title: String, value: String) {

    }

    fun putCheckList(title: String, beforeValue: String, afterValue: String) {

    }
}