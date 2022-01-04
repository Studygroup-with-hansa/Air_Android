package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.usecase.user.PostRequestAuthUseCase
import com.hansarang.android.domain.usecase.user.PutSendAuthCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postRequestAuthUseCase: PostRequestAuthUseCase,
    private val putSendAuthCodeUseCase: PutSendAuthCodeUseCase
) : ViewModel() {

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> get() = _isFailure

    private val _isAuthSuccess = MutableLiveData<String>()
    val isAuthSuccess: LiveData<String> get() = _isAuthSuccess
    
    private val _isEmailSent = MutableLiveData<Boolean>()
    val isEmailSent: LiveData<Boolean> get() = _isEmailSent

    private val _isEmailExist = MutableLiveData<Boolean>()
    val isEmailExist: LiveData<Boolean> get() = _isEmailExist

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun getRequestAuth(email: String) {
        _isLoading.value = true

        viewModelScope.launch {
            if (email.isNotEmpty()) {
                val params = PostRequestAuthUseCase.Params(email)
                try {
                    withTimeout(10000) {
                        val emailChk = Patterns.EMAIL_ADDRESS
                        if (emailChk.matcher(email).matches()) {
                            val auth = postRequestAuthUseCase.buildParamsUseCaseSuspend(params)
                            _isEmailSent.value = auth.emailSent
                            _isEmailExist.value = auth.isEmailExist
                        } else {
                            _isEmailSent.value = false
                            _isEmailExist.value = false
                            _isFailure.value =
                                Event("이메일 전송에 실패하였습니다. 재시도 해주세요.")
                        }
                    }
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                    _isEmailSent.value = false
                } catch (e: Throwable) {
                    _isFailure.value = if (e.message == "400") {
                        Event("이메일 전송에 실패하였습니다. 재시도 해주세요.")
                    } else {
                        Log.d("SignInViewModel", "getRequestAuth: ${e.message}")
                        Event("오류 발생")
                    }
                    _isEmailSent.value = false

                } finally {
                    _isLoading.value = false
                }
            } else {
                _isFailure.value = Event("이메일을 입력해 주세요.")
                _isLoading.value = false
            }
        }
    }

    fun postSendAuthCode(email: String, authCode: String) {
        _isLoading.value = true

        viewModelScope.launch {
            if (authCode.isNotEmpty()) {
                val params = PutSendAuthCodeUseCase.Params(email, authCode)
                try {
                    withTimeout(10000) {
                        val token = putSendAuthCodeUseCase.buildParamsUseCaseSuspend(params)
                        _isAuthSuccess.value = token.token
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
                } finally {
                    _isLoading.value = false
                }
            } else {
                _isFailure.value = Event("인증번호를 입력해 주세요.")
                _isLoading.value = false
            }
        }
    }
}