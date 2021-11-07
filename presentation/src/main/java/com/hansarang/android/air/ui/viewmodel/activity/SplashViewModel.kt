package com.hansarang.android.air.ui.viewmodel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.usecase.user.GetCheckTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCheckTokenUseCase: GetCheckTokenUseCase
): ViewModel() {
    private val _isSuccess = MutableLiveData<String>()
    val isSuccess: LiveData<String> = _isSuccess

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    fun checkToken() {
        viewModelScope.launch {
            try {
                _isSuccess.value = getCheckTokenUseCase.buildUseCaseSuspend()
            } catch (e: Throwable) {
                when(e.message) {
                    "401" -> _isFailure.value = "유효하지 않은 토큰입니다. 로그인으로 이동합니다."
                    else -> _isFailure.value = e.message
                }
            }
        }
    }
}