package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel: ViewModel() {
    val lightMode = MutableLiveData(true)
    val darkMode = MutableLiveData(false)
    val systemMode = MutableLiveData(false)
}
