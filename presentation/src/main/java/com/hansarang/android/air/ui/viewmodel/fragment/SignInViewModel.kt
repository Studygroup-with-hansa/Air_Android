package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.usecase.user.GetRequestAuthUseCase
import com.hansarang.android.domain.usecase.user.PostSendAuthCodeUseCase
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class SignInViewModel(
    private val getRequestAuthUseCase: GetRequestAuthUseCase,
    private val postSendAuthCodeUseCase: PostSendAuthCodeUseCase
) : ViewModel() {

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isAuthSuccess = MutableLiveData<Event<String>>()
    val isAuthSuccess: LiveData<Event<String>> = _isAuthSuccess

    private val _isExistEmail = MutableLiveData<Event<String>>()
    val isExistEmail: LiveData<Event<String>> = _isExistEmail

    val email = MutableLiveData<String>()
    val authCode = MutableLiveData<String>()

    fun getRequestAuth() {
        val email = email.value?:""

        viewModelScope.launch {
            if (email.isNotEmpty()) {
                val params = GetRequestAuthUseCase.Params(email)
                try {
                    withTimeout(10000) {
                        getRequestAuthUseCase.buildParamsUseCaseSuspend(params)
                    }
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                } catch (e: Throwable) {
                    when (e.message ?: "") {
                        "400" -> _isExistEmail.value = Event("이미 계정이 존재합니다. 메인 화면으로 이동합니다.")
                        else -> _isFailure.value = Event("오류 발생")
                    }
                }
            } else {
                _isFailure.value = Event("이메일을 입력해 주세요.")
            }
        }
    }

    fun postSendAuthCode() {
        val email = email.value?:""
        val authCode = authCode.value?:""

        viewModelScope.launch {
            if (authCode.isNotEmpty()) {
                val params = PostSendAuthCodeUseCase.Params(email, authCode)
                try {
                    withTimeout(10000) {
                        val token = postSendAuthCodeUseCase.buildParamsUseCaseSuspend(params)
                        _isAuthSuccess.value = Event(token.token)
                    }
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                } catch (e: Throwable) {
                    val failureStatus = e.message ?: ""
                    when (failureStatus) {
                        "401" -> Event("인증 코드가 잘못되었습니다. 재시도 해주세요.")
                        "410" -> Event("시간이 초과된 인증 코드입니다. 재시도 해주세요.")
                        else -> Event("오류 발생")
                    }.also { _isFailure.value = it }
                }
            } else {
                _isFailure.value = Event("인증번호를 입력해 주세요.")
            }
        }
    }
}