package com.hansarang.android.air.ui.viewmodel.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.usecase.subject.GetTargetTimeUseCase
import com.hansarang.android.domain.usecase.subject.PostTargetTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SetGoalDialogViewModel @Inject constructor(
    private val getTargetTimeUseCase: GetTargetTimeUseCase,
    private val postTargetTimeUseCase: PostTargetTimeUseCase
): ViewModel() {
    val goal = MutableLiveData(0L)

    val hour = MutableLiveData<String>()
    val minute = MutableLiveData<String>()
    val second = MutableLiveData<String>()

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure = MutableLiveData<Event<String>>()

    private val _isDismissed = MutableLiveData<Event<String>>()
    val isDismissed: LiveData<Event<String>> = _isDismissed

    fun getTargetTime() {
        viewModelScope.launch {
            try {
                val targetTime = getTargetTimeUseCase.buildUseCaseSuspend()
                goal.value = targetTime.targetTime
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message?:"")
            }
        }
    }

    fun onHourChanged(s: CharSequence) {
        if (s.isNotEmpty()) {
            val hourInt =
                if (s.toString().isEmpty()) 0
                else s.toString().toInt()

            when {
                s == "00" && hourInt == 0 ->
                    hour.value = "00"
                hourInt <= 99 ->
                    hour.value = String.format("%02d", hourInt)
                else ->
                    hour.value = String.format("%02d", hourInt / 100)
            }
        }
    }

    fun onMinuteChanged(s: CharSequence) {
        if (s.isNotEmpty()) {
            val min =
                if (s.toString().isEmpty()) 0
                else s.toString().toInt()

            when {
                s == "00" && min == 0 -> {
                    minute.value = "00"
                }
                min <= 99 -> {
                    if (min >= 60) {
                        hour.value =
                            String.format("%02d",
                                if (hour.value != null && hour.value!!.isNotEmpty()) {
                                    (hour.value!!.toInt()) + 1
                                } else 1
                            )
                        minute.value = String.format("%02d", min - 60)
                        return
                    }
                    minute.value = String.format("%02d", min)
                }
                else -> {
                    minute.value = String.format("%02d", min / 100)
                }
            }
        }
    }

    fun onSecondChanged(s: CharSequence) {
        if (s.isNotEmpty()) {
            val sec =
                if (s.toString().isEmpty()) 0
                else s.toString().toInt()

            when {
                s == "00" && sec == 0 -> {
                    second.value = "00"
                }
                sec <= 99 -> {
                    if (sec >= 60) {
                        minute.value =
                            String.format("%02d",
                                if (minute.value != null && minute.value!!.isNotEmpty()) {
                                    (minute.value!!.toInt()) + 1
                                } else 1
                            )
                        second.value = String.format("%02d", sec - 60)
                        return
                    }
                    second.value = String.format("%02d", sec)
                }
                else -> {
                    second.value = String.format("%02d", sec / 100)
                }
            }
        }
    }

    fun onClickCancel() {
        _isDismissed.value = Event("취소되었습니다.")
    }

    fun onClickSave() {

        val hour = ((hour.value?:"0").toLong()) * 3600
        val min = (minute.value?:"0").toLong() * 60
        val sec = (second.value?:"0").toLong()

        if (hour + min + sec != 0L) {
            Log.d("TAG", "onClickSave: asdfs")
            viewModelScope.launch {
                try {
                    withTimeout(10000) {
                        val params = PostTargetTimeUseCase.Params(hour + min + sec)
                        postTargetTimeUseCase.buildParamsUseCaseSuspend(params)
                        _isDismissed.value = Event("완료되었습니다.")
                    }
                } catch (e: Throwable) {
                    _isFailure.value = Event(e.message?:"")
                } catch (e: TimeoutCancellationException) {
                    _isFailure.value = Event("시간 초과")
                }
            }
        } else {
            _isDismissed.value = Event("완료되었습니다.")
        }
    }
}
