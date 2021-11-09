package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.GroupRank
import com.hansarang.android.domain.usecase.group.DeleteGroupUseCase
import com.hansarang.android.domain.usecase.group.DeleteGroupUserUseCase
import com.hansarang.android.domain.usecase.group.DeleteLeaveGroupUseCase
import com.hansarang.android.domain.usecase.group.PostViewGroupDetail
import com.hansarang.android.domain.usecase.user.GetUserBasicInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val postViewGroupDetail: PostViewGroupDetail,
    private val getUserBasicInfoUseCase: GetUserBasicInfoUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val deleteLeaveGroupUseCase: DeleteLeaveGroupUseCase,
    private val deleteGroupUserUseCase: DeleteGroupUserUseCase
): ViewModel() {

    private val _isGroupLeader = MutableLiveData(false)
    val isGroupLeader: LiveData<Boolean> = _isGroupLeader

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    val groupCode = MutableLiveData<String>()
    val leader = MutableLiveData<String>()
    val leaderEmail = MutableLiveData<String>()

    private val _groupRankList = MutableLiveData<List<GroupRank>>()
    val groupRankList: LiveData<List<GroupRank>> = _groupRankList

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isLeaveSuccess = MutableLiveData<Event<String>>()
    val isLeaveSuccess: LiveData<Event<String>> = _isLeaveSuccess

    fun getIsGroupLeader() {
        viewModelScope.launch {
            try {
                val user = getUserBasicInfoUseCase.buildUseCaseSuspend()
                _isGroupLeader.value = (leaderEmail.value?:"") == user.email
            } catch (e: Throwable) {
            }
        }
    }

    fun getRank() {
        _isLoading.value = true
        val groupCode = groupCode.value?:""
        viewModelScope.launch {
            try {
                delay(1000)
                val params = PostViewGroupDetail.Params(groupCode)
                _groupRankList.value = postViewGroupDetail.buildParamsUseCaseSuspend(params).userList
            } catch (e: Throwable) {
                _isFailure.value = when(e.message) {
                    "400" -> Event("그룹 코드가 유효하지 않습니다.")
                    else -> Event("코드 ${e.message} 오류가 발생했습니다.")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteGroup() {
        viewModelScope.launch {
            try {
                _isLeaveSuccess.value =
                    Event(deleteGroupUseCase.buildUseCaseSuspend())
            } catch (e: Throwable) {
                _isFailure.value = when(e.message) {
                    "401" -> Event("토큰이 유효하지 않습니다.")
                    "409" -> Event("그룹이 존재하지 않습니다.")
                    else -> Event("코드 ${e.message} 오류 발생")
                }
            }
        }
    }

    fun leaveGroup() {

        val groupCode = groupCode.value ?: ""

        viewModelScope.launch {
            try {
                val params = DeleteLeaveGroupUseCase.Params(groupCode)
                _isLeaveSuccess.value =
                    Event(deleteLeaveGroupUseCase.buildParamsUseCaseSuspend(params))
            } catch (e: Throwable) {
                _isFailure.value = when(e.message) {
                    "401" -> Event("토큰이 유효하지 않습니다.")
                    "400" -> Event("그룹장은 탈퇴할 수 없습니다.")
                    else -> Event("코드 ${e.message} 오류 발생")
                }
            }
        }
    }

    fun leaveUserGroup(groupRank: GroupRank) {

        val groupCode = groupCode.value ?: ""

        if (leaderEmail.value ?: "" != groupRank.email) {
            viewModelScope.launch {
                try {
                    val params = DeleteGroupUserUseCase.Params(groupRank.email, groupCode)
                    _isLeaveSuccess.value =
                        Event(deleteGroupUserUseCase.buildParamsUseCaseSuspend(params).code)
                } catch (e: Throwable) {
                    _isFailure.value = when (e.message) {
                        "401" -> Event("토큰이 유효하지 않습니다.")
                        "409" -> Event("자신은 내보낼 수 없습니다.")
                        else -> Event("코드 ${e.message} 오류 발생")
                    }
                }
            }
        } else {
            _isFailure.value = Event("자신은 내보낼 수 없습니다.")
        }
    }
}
