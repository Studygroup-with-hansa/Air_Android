package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.adapter.WeekdayDatePickerAdapterViewModel

class WeekdayDatePickerAdapterViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeekdayDatePickerAdapterViewModel::class.java)) {
            return WeekdayDatePickerAdapterViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }

}