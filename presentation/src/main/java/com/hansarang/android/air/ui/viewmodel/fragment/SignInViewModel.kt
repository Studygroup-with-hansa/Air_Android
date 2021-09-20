package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.usecase.user.GetRequestAuthUseCase
import com.hansarang.android.domain.usecase.user.PostSendAuthCodeUseCase
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class SignInViewModel(
    private val getRequestAuthUseCase: GetRequestAuthUseCase,
    private val postSendAuthCodeUseCase: PostSendAuthCodeUseCase
) : ViewModel() {

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    val email = MutableLiveData<String>()
    val authCode = MutableLiveData<String>()

    fun getRequestAuth() {
        val email = email.value?:""

        viewModelScope.launch {
            val params = GetRequestAuthUseCase.Params(email)
            try {
                withTimeout(10000) {
                    getRequestAuthUseCase.buildParamsUseCaseSuspend(params)
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = "시간 초과"
            } catch (e: Throwable) {
                val failureStatus = e.message?:""
                when(failureStatus) {
                    "400" -> "요청한 이메일이 이미 존재합니다."
                    else -> ""
                }.also { _isFailure.value = it }
            }
        }
    }

    fun postSendAuthCode() {
        val email = email.value?:""
        val authCode = authCode.value?:""

        viewModelScope.launch {
            val params = PostSendAuthCodeUseCase.Params(email, authCode)
            try {
                withTimeout(10000) {
                    val token = postSendAuthCodeUseCase.buildParamsUseCaseSuspend(params)
                    // 토큰 저장 코드
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = "시간 초과"
            } catch (e: Throwable) {
                val failureStatus = e.message?:""
                when(failureStatus) {
                    "401" -> "인증 코드가 잘못되었습니다. 재시도 해주세요."
                    "410" -> "시간이 초과된 인증 코드입니다. 재시도 해주세요."
                    else -> ""
                }.also { _isFailure.value = it }
            }
        }
    }
}