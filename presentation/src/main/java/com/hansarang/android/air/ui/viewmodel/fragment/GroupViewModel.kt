package com.hansarang.android.air.ui.viewmodel.fragment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.Group
import com.hansarang.android.domain.usecase.group.GetGroupListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getGroupListUseCase: GetGroupListUseCase
): ViewModel() {
    val progressBarVisibility = MutableLiveData(View.GONE)

    private val _groupList = MutableLiveData<ArrayList<Group>>()
    val groupList: LiveData<ArrayList<Group>> = _groupList

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    fun groupList() {
        viewModelScope.launch {
            try {
                _groupList.value = ArrayList(getGroupListUseCase.buildUseCaseSuspend().groupList)
            } catch (e: Throwable) {
                _isFailure.value = when(e.message) {
                    "500" -> Event("서버 오류입니다.")
                    "401" -> Event("토큰이 유효하지 않습니다.")
                    else -> Event("코드 ${e.message} 오류가 발생했습니다.")
                }
            }
        }
    }
}
