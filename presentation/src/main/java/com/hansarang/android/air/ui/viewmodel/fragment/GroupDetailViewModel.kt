package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.*
import com.hansarang.android.air.di.assistedfactory.GroupDetailAssistedFactory
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.air.ui.livedata.SingleLiveEvent
import com.hansarang.android.domain.entity.dto.GroupRank
import com.hansarang.android.domain.usecase.group.DeleteGroupUseCase
import com.hansarang.android.domain.usecase.group.DeleteGroupUserUseCase
import com.hansarang.android.domain.usecase.group.DeleteLeaveGroupUseCase
import com.hansarang.android.domain.usecase.group.PostViewGroupDetail
import com.hansarang.android.domain.usecase.user.GetUserBasicInfoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GroupDetailViewModel @AssistedInject constructor(
    private val postViewGroupDetail: PostViewGroupDetail,
    private val getUserBasicInfoUseCase: GetUserBasicInfoUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase,
    private val deleteLeaveGroupUseCase: DeleteLeaveGroupUseCase,
    private val deleteGroupUserUseCase: DeleteGroupUserUseCase,
    @Assisted("groupCode") val groupCode: String,
    @Assisted("leader") val leader: String,
    @Assisted("leaderEmail") val leaderEmail: String
): ViewModel() {

    private val _isGroupLeader = MutableLiveData(false)
    val isGroupLeader: LiveData<Boolean> get() = _isGroupLeader

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _groupRankList = MutableLiveData<List<GroupRank>>()
    val groupRankList: LiveData<List<GroupRank>> get() = _groupRankList

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> get() = _isFailure

    private val _isLeaveSuccess = MutableLiveData<Event<String>>()
    val isLeaveSuccess: LiveData<Event<String>> get() = _isLeaveSuccess

    private val _copyCodeButtonLongClick = SingleLiveEvent<Unit>()
    val copyCodeButtonLongClick: LiveData<Unit> get() = _copyCodeButtonLongClick

    private val _backButtonClick = SingleLiveEvent<Unit>()
    val backButtonClick: LiveData<Unit> get() = _backButtonClick

    private val _groupExitButtonClick = SingleLiveEvent<Unit>()
    val groupExitButtonClick: LiveData<Unit> get() = _groupExitButtonClick

    private val _deleteGroupButtonClick = SingleLiveEvent<Unit>()
    val deleteGroupButtonClick: LiveData<Unit> get() = _deleteGroupButtonClick

    fun onCopyGroupCodeButtonLongClick(): Boolean {
        _copyCodeButtonLongClick.call()
        return true
    }

    fun onBackButtonClick() {
        _backButtonClick.call()
    }

    fun onGroupExitButtonClick() {
        _groupExitButtonClick.call()
    }

    fun onDeleteGroupButtonClick() {
        _deleteGroupButtonClick.call()
    }

    fun getRank() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                delay(1000)
                val user = getUserBasicInfoUseCase.buildUseCaseSuspend()
                _isGroupLeader.value = leaderEmail == user.email

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
        if (leaderEmail != groupRank.email) {
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

    companion object {
        fun provideFactory(
            assistedFactory: GroupDetailAssistedFactory,
            groupCode: String,
            leader: String,
            leaderEmail: String
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(groupCode, leader, leaderEmail) as T
            }
        }
    }
}
