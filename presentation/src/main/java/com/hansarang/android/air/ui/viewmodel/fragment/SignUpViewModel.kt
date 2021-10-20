package com.hansarang.android.air.ui.viewmodel.fragment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.usecase.user.PutModifyUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val putModifyUsernameUseCase: PutModifyUsernameUseCase
): ViewModel() {

    val progressBarVisibility = MutableLiveData(View.GONE)

    private val _isSuccess = MutableLiveData<Event<String>>()
    val isSuccess: LiveData<Event<String>> = _isSuccess

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    val finishButtonEnabled = MutableLiveData(false)

    val nickname = MutableLiveData<String>()

    fun putModifyUsername() {
        progressBarVisibility.value = View.VISIBLE

        val nickname = nickname.value?:""

        viewModelScope.launch {
            if (nickname.isNotEmpty()) {
                val params = PutModifyUsernameUseCase.Params(nickname)
                try {
                    withTimeout(10000) {
                        putModifyUsernameUseCase.buildParamsUseCaseSuspend(params)
                        _isSuccess.value = Event("Success")
                    }
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                } catch (e: Throwable) {
                    when (e.message) {
                        "401" -> _isFailure.value = Event("유효하지 않은 토큰입니다.")
                        else -> _isFailure.value = Event("오류 발생")
                    }
                } finally {
                    progressBarVisibility.value = View.GONE
                }
            } else {
                _isFailure.value = Event("이메일을 입력해 주세요.")
                progressBarVisibility.value = View.GONE
            }
        }
    }
}