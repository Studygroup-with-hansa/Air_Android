package com.hansarang.android.air.ui.viewmodel.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.usecase.timer.PostTimerStartUseCase
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val postTimerStartUseCase: PostTimerStartUseCase,
    private val postTimerStopUseCase: PostTimerStopUseCase
): ViewModel() {
    val title = MutableLiveData<String>()
    val date = MutableLiveData<Long>()
    val time = MutableLiveData(0L)
    val goal = MutableLiveData<Long>()
    val isStarted = MutableLiveData<Boolean>()

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> = _isFailure

    fun postTimerStart() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postTimerStartUseCase.buildParamsUseCaseSuspend(
                    PostTimerStartUseCase.Params(title.value ?: "")
                )
            } catch (e: Throwable) {
                when(e.message) {
                    "401" -> _isFailure.postValue("유효하지 않은 토큰입니다.")
                    "409" -> {

                    }
                    else -> {
                        _isFailure.postValue("${e.message} 오류 발생")
                        Log.d("TimerViewModel", "postTimerStart: ${e.message}")
                    }
                }
            }
        }
    }

    fun postTimerStop() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                postTimerStopUseCase.buildParamsUseCaseSuspend(
                    PostTimerStopUseCase.Params(title.value ?: "")
                )
            } catch (e: Throwable) {
                when(e.message) {
                    "401" -> _isFailure.postValue("유효하지 않은 토큰입니다.")
                    "409" -> {

                    }
                    else -> {
                        _isFailure.postValue("${e.message} 오류 발생")
                        Log.d("TimerViewModel", "postTimerStop: ${e.message}")
                    }
                }
            }
        }
    }
}
