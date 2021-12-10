package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.air.ui.livedata.SingleLiveEvent

class SettingViewModel: ViewModel() {
    val lightMode = MutableLiveData<Boolean>()
    val darkMode = MutableLiveData<Boolean>()
    val systemMode = MutableLiveData<Boolean>()

    private val _logoutButtonClick = SingleLiveEvent<Unit>()
    val logoutButtonClick: LiveData<Unit> get() = _logoutButtonClick

    fun onLogoutButtonClick() {
        _logoutButtonClick.call()
    }
}
