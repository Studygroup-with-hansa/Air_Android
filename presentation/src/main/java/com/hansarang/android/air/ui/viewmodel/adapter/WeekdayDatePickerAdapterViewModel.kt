package com.hansarang.android.air.ui.viewmodel.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.Stats

class WeekdayDatePickerAdapterViewModel: ViewModel() {
    val stats = MutableLiveData<Stats>()
}