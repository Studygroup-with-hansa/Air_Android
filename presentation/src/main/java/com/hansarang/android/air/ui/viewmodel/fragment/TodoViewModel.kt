package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.Todo
import java.util.*

class TodoViewModel: ViewModel() {
    private val _date = MutableLiveData(System.currentTimeMillis())
    val date: LiveData<Long> = _date

    private val _todoList = MutableLiveData(ArrayList<Todo>())
    val todoList: LiveData<ArrayList<Todo>> = _todoList

    private val _subjectTodoList = MutableLiveData(ArrayList<String>())
    val subjectTodoList: LiveData<ArrayList<String>> = _subjectTodoList

    fun setDay(currentYMD: String, amount: Int) {
        val ymdArray = currentYMD.split(".").map { it.toInt() }
        val calendar = Calendar.Builder()
            .setDate(ymdArray[0], ymdArray[1] - 1, ymdArray[2])
            .build()
        calendar.add(Calendar.DAY_OF_MONTH, amount)
        _date.value = calendar.timeInMillis
    }

    fun getTodoList() {
        _todoList.value = arrayListOf(Todo("국어"), Todo("수학"), Todo("한국사"), Todo("ㅎㅎㅎ"))
    }

    fun getSubjectTodoList() {
        _subjectTodoList.value= arrayListOf("dzfsd", "ㅁㄴㅇㄹㅁㅇㄴㄹㅁㄹ", "ㅁㅇㄹㅁㄴㄹㅁㄹㅇ", "ㅁㅇㄹㅁㄴㄹㅁㄹ")
    }
}