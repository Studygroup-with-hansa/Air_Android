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
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val putModifyUsernameUseCase: PutModifyUsernameUseCase
): ViewModel() {

    lateinit var image: MultipartBody.Part

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    private val _isSuccess = MutableLiveData<Event<String>>()
    val isSuccess: LiveData<Event<String>> = _isSuccess

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    val finishButtonEnabled = MutableLiveData(false)

    val nickname = MutableLiveData<String>()

    fun putModifyUsername() {
        _isLoading.value = true

        val nickname = nickname.value?:""

        viewModelScope.launch {
            if (nickname.isNotEmpty() && this@SignUpViewModel::image.isInitialized) {
                val params = PutModifyUsernameUseCase.Params(nickname)
                try {
                    withTimeout(10000) {
                        if (nickname.length !in 2..8 || nickname.isEmpty()) {
                            _isSuccess.value = Event("닉네임은 두자 이상 8자 이내로 입력해 주세요.")
                        } else {
                            putModifyUsernameUseCase.buildParamsUseCaseSuspend(params)
                            _isSuccess.value = Event("Success")
                        }
                    }
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                } catch (e: Throwable) {
                    when (e.message) {
                        "401" -> _isFailure.value = Event("유효하지 않은 토큰입니다.")
                        else -> _isFailure.value = Event("${e.message} 오류 발생")
                    }
                } finally {
                    _isLoading.value = false
                }
            } else {
                _isFailure.value = Event("이메일을 입력하거나 사진을 추가해 주세요.")
                _isLoading.value = false
            }
        }
    }
}