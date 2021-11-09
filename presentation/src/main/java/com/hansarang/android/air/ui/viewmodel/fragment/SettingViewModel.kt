package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel: ViewModel() {
    val lightMode = MutableLiveData<Boolean>()
    val darkMode = MutableLiveData<Boolean>()
    val systemMode = MutableLiveData<Boolean>()
}
