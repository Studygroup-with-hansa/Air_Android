package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.Todo
import java.util.*
import kotlin.collections.ArrayList

class TodoViewModel: ViewModel() {
    private val _date = MutableLiveData(System.currentTimeMillis())
    val date: LiveData<Long> = _date

    private val _todoList = MutableLiveData(ArrayList<Todo>())
    val todoList: LiveData<ArrayList<Todo>> = _todoList

    fun setDay(currentYMD: String, amount: Int) {
        val ymdArray = currentYMD.split(".").map { it.toInt() }
        val calendar = Calendar.Builder()
            .setDate(ymdArray[0], ymdArray[1] - 1, ymdArray[2])
            .build()
        calendar.add(Calendar.DAY_OF_MONTH, amount)
        _date.value = calendar.timeInMillis
    }

    fun getTodoList() {
        _todoList.value = arrayListOf(
            Todo("국어",
                arrayListOf("dzfsd", "ㅁㄴㅇㄹㅁㅇㄴㄹㅁㄹ", "ㅁㅇㄹㅁㄴㄹㅁㄹㅇ", "ㅁㅇㄹㅁㄴㄹㅁㄹ")
            ),
            Todo("수학",
                arrayListOf("dzfsd", "ㅁㄴㅇㄹㅁㅇㄴㄹㅁㄹ", "ㅁㅇㄹㅁㄴㄹㅁㄹㅇ", "ㅁㅇㄹㅁㄴㄹㅁㄹ")
            ),
            Todo("한국사",
                arrayListOf("dzfsd", "ㅁㄴㅇㄹㅁㅇㄴㄹㅁㄹ", "ㅁㅇㄹㅁㄴㄹㅁㄹㅇ", "ㅁㅇㄹㅁㄴㄹㅁㄹ")
            ),
            Todo("ㅎㅎㅎ",
                arrayListOf("dzfsd", "ㅁㄴㅇㄹㅁㅇㄴㄹㅁㄹ", "ㅁㅇㄹㅁㄴㄹㅁㄹㅇ", "ㅁㅇㄹㅁㄴㄹㅁㄹ")
            )
        )
    }

    fun postCheckList(title: String, value: String) {
        if (!_todoList.value.isNullOrEmpty() && title.isNotEmpty() && value.isNotEmpty()) {
            _todoList.value = ArrayList(_todoList.value!!.map {
                if (it.title == title) {
                    it.checkList.add(value)
                    it
                } else {
                    it
                }
            })
        }
    }

    fun deleteCheckList(title: String, value: String) {
        if (!_todoList.value.isNullOrEmpty() && title.isNotEmpty() && value.isNotEmpty()) {
            _todoList.value = ArrayList(_todoList.value!!.map {
                if (it.title == title) {
                    it.checkList.remove(value)
                    it
                } else {
                    it
                }
            })
        }
    }

    fun putCheckList(title: String, beforeValue: String, afterValue: String) {
        if (!_todoList.value.isNullOrEmpty() && title.isNotEmpty() && afterValue.isNotEmpty()) {
            _todoList.value = ArrayList(_todoList.value!!.map {
                if (it.title == title) {
                    it.checkList.map { value ->
                        if (value == beforeValue) {
                            afterValue
                        } else {
                            value
                        }
                    }
                    it
                } else {
                    it
                }
            })
        }
    }
}