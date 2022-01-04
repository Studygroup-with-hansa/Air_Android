package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.air.ui.livedata.SingleLiveEvent
import com.hansarang.android.domain.entity.dto.Group
import com.hansarang.android.domain.usecase.group.GetGroupListUseCase
import com.hansarang.android.domain.usecase.group.PostCreateUserGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val getGroupListUseCase: GetGroupListUseCase,
    private val postCreateUserGroupUseCase: PostCreateUserGroupUseCase
): ViewModel() {
    var isFirstLoad = true

    private val _groupList = MutableLiveData<ArrayList<Group>>()
    val groupList: LiveData<ArrayList<Group>> get() = _groupList

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> get() = _isFailure

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isEmpty = MutableLiveData(false)
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private val _createGroupButtonClick = SingleLiveEvent<Unit>()
    val createGroupButtonClick: LiveData<Unit> get() = _createGroupButtonClick

    private val _joinGroupButtonClick = SingleLiveEvent<Unit>()
    val joinGroupButtonClick: LiveData<Unit> get() = _joinGroupButtonClick

    fun onCreateGroupButtonClick() {
        _createGroupButtonClick.call()
    }

    fun onJoinGroupButtonClick() {
        _joinGroupButtonClick.call()
    }

    fun groupList() {
        _isLoading.value = true

        viewModelScope.launch {
            delay(1000)
            try {
                val baseGroup = getGroupListUseCase.buildUseCaseSuspend()
                val groupList = ArrayList(baseGroup.groupList)
                _groupList.value = groupList
                _isEmpty.value = groupList.isEmpty()
            } catch (e: Throwable) {
                _isEmpty.value = true
                _isFailure.value = when(e.message) {
                    "500" -> Event("서버 오류입니다.")
                    "401" -> Event("토큰이 유효하지 않습니다.")
                    else -> Event("코드 ${e.message} 오류가 발생했습니다.")
                }
            } finally {
                _isLoading.value = false
                isFirstLoad = false
            }
        }
    }

    fun postGroup() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                postCreateUserGroupUseCase.buildUseCaseSuspend()
            } catch (e: Throwable) {
                _isFailure.value = when(e.message) {
                    "500" -> Event("서버 오류입니다.")
                    "401" -> Event("토큰이 유효하지 않습니다.")
                    "409" -> Event("내 그룹이 이미 존재합니다.")
                    else -> Event("코드 ${e.message} 오류가 발생했습니다.")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
