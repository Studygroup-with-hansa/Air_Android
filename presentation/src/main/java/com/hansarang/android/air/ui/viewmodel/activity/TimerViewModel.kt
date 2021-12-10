package com.hansarang.android.air.ui.viewmodel.activity

import android.util.Log
import androidx.lifecycle.*
import com.hansarang.android.air.di.assistedfactory.TimerAssistedFactory
import com.hansarang.android.air.ui.livedata.SingleLiveEvent
import com.hansarang.android.domain.usecase.timer.PostTimerStartUseCase
import com.hansarang.android.domain.usecase.timer.PostTimerStopUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TimerViewModel @AssistedInject constructor(
    private val postTimerStartUseCase: PostTimerStartUseCase,
    private val postTimerStopUseCase: PostTimerStopUseCase,
    @Assisted("title") val title: String,
    @Assisted("date") val date: Long,
    @Assisted("time") time: Long,
    @Assisted("goal") val goal: Long,
    @Assisted("isStarted") isStarted: Boolean
): ViewModel() {

    val time = MutableLiveData(time)

    private val _isStarted = SingleLiveEvent<Unit>()
    val isStarted: LiveData<Unit> get() = _isStarted
    init {
        if (isStarted) _isStarted.call()
    }

    private val _isStopped = SingleLiveEvent<Unit>()
    val isStopped: LiveData<Unit> get() = _isStopped

    private val _isFailure = MutableLiveData<String>()
    val isFailure: LiveData<String> get() = _isFailure

    private val _backButtonClick = SingleLiveEvent<Unit>()
    val backButtonClick: LiveData<Unit> get() = _backButtonClick

    fun onPlayButtonClick(isPlayed: Boolean) {
        if (isPlayed) _isStarted.call()
        else _isStopped.call()
    }

    fun onBackButtonClick() {
        _backButtonClick.call()
    }

    fun postTimerStart() {
        viewModelScope.launch {
            try {
                postTimerStartUseCase.buildParamsUseCaseSuspend(
                    PostTimerStartUseCase.Params(title)
                )
            } catch (e: Throwable) {
                when(e.message) {
                    "401" -> _isFailure.value = "유효하지 않은 토큰입니다."
                    else -> {
                        _isFailure.value = "${e.message} 오류 발생"
                        Log.d("TimerViewModel", "postTimerStart: ${e.message}")
                    }
                }
            }
        }
    }

    fun postTimerStop() {
        viewModelScope.launch {
            try {
                postTimerStopUseCase.buildParamsUseCaseSuspend(
                    PostTimerStopUseCase.Params(title)
                )
            } catch (e: Throwable) {
                when(e.message) {
                    "401" -> _isFailure.value = "유효하지 않은 토큰입니다."
                    "409" -> { }
                    else -> {
                        _isFailure.value = "${e.message} 오류 발생"
                        Log.d("TimerViewModel", "postTimerStop: ${e.message}")
                    }
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: TimerAssistedFactory,
            date: Long,
            title: String,
            time: Long,
            goal: Long,
            isStarted: Boolean
        ): ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(title, date, time, goal, isStarted) as T
            }
        }
    }
}
