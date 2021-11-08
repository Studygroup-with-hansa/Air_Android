package com.hansarang.android.air.ui.viewmodel.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.usecase.group.PutJoinGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class JoinGroupDialogViewModel @Inject constructor(
    private val putJoinGroupUseCase: PutJoinGroupUseCase
): ViewModel() {

    val groupCode = MutableLiveData<String>()

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isDismissed = MutableLiveData<Event<String>>()
    val isDismissed: LiveData<Event<String>> = _isDismissed

    fun onClickCancel() {
        _isDismissed.value = Event("취소되었습니다.")
    }

    fun onClickSave() {

        val groupCode = groupCode.value ?: ""

        if (groupCode.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    withTimeout(10000) {
                        val params = PutJoinGroupUseCase.Params(groupCode)
                        putJoinGroupUseCase.buildParamsUseCaseSuspend(params)
                        _isDismissed.value = Event("완료되었습니다.")
                    }
                } catch (e: Throwable) {
                    _isFailure.value = when(e.message) {
                        "400" -> Event("그룹 코드가 잘못되었습니다.")
                        else -> Event("코드 ${e.message} 오류 발생")
                    }
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                }
            }
        } else {
            _isFailure.value = Event("과목 코드를 입력해 주세요.")
        }
    }
}
