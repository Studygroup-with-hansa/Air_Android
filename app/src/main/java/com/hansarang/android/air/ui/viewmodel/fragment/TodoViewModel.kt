package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class TodoViewModel: ViewModel() {
    private val _date = MutableLiveData(System.currentTimeMillis())
    val date: LiveData<Long> = _date

    fun setDay(currentYMD: String, amount: Int) {
        val ymdArray = currentYMD.split(".").map { it.toInt() }
        val calendar = Calendar.Builder()
            .setDate(ymdArray[0], ymdArray[1] - 1, ymdArray[2])
            .build()
        calendar.add(Calendar.DAY_OF_MONTH, amount)
        _date.value = calendar.timeInMillis
    }
}