package com.hansarang.android.air.ui.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel: ViewModel() {
    val title = MutableLiveData<String>()
    val date = MutableLiveData<Long>()
    val time = MutableLiveData<Long>()
    val goal = MutableLiveData<Long>()
}
