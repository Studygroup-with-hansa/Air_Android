package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.GroupRank
import com.hansarang.android.domain.usecase.group.PostViewGroupDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(
    private val postViewGroupDetail: PostViewGroupDetail
): ViewModel() {

    val groupCode = MutableLiveData<String>()
    val leader = MutableLiveData<String>()

    private val _groupRankList = MutableLiveData<List<GroupRank>>()
    val groupRankList: LiveData<List<GroupRank>> = _groupRankList

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    fun getRank() {
        val groupCode = groupCode.value?:""
        viewModelScope.launch {
            try {
                val params = PostViewGroupDetail.Params(groupCode)
                _groupRankList.value = postViewGroupDetail.buildParamsUseCaseSuspend(params).userList
            } catch (e: Throwable) {
                _isFailure.value = when(e.message) {
                    "400" -> Event("그룹 코드가 유효하지 않습니다.")
                    else -> Event("코드 ${e.message} 오류가 발생했습니다.")
                }
            }
        }
    }
}
