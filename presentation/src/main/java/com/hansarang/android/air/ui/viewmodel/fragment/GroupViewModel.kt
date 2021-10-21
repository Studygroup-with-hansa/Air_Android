package com.hansarang.android.air.ui.viewmodel.fragment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.Group
import dagger.hilt.android.lifecycle.HiltViewModel

class GroupViewModel(): ViewModel() {
    val progressBarVisibility = MutableLiveData(View.GONE)

    private val _groupList = MutableLiveData<ArrayList<Group>>()
    val groupList: LiveData<ArrayList<Group>> = _groupList

    fun groupList() {
        _groupList.value = arrayListOf(
            Group("asdfasfaf", "리더", "1등", 60, 6),
            Group("asdfasfaf", "리더2", "1등", 180, 6),
            Group("asdfasfaf", "리더3", "1등", 360, 6),
        )
    }
}
