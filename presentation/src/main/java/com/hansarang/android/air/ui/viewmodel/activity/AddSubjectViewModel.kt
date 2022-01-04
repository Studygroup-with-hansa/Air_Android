package com.hansarang.android.air.ui.viewmodel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.Subject
import com.hansarang.android.domain.usecase.subject.PostSubjectUseCase
import com.hansarang.android.domain.usecase.subject.PutSubjectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSubjectViewModel @Inject constructor(
    private val postSubjectUseCase: PostSubjectUseCase,
    private val putSubjectUseCase: PutSubjectUseCase
): ViewModel() {

    val oldTitle = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val color = MutableLiveData<String>()
    val submitBtnText = MutableLiveData<String>()

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _isSuccess = MutableLiveData<Event<String>>()
    val isSuccess: LiveData<Event<String>> = _isSuccess

    fun addSubject() {
        val title = title.value?:""
        val color = color.value?:""

        viewModelScope.launch {
            try {
                if (title.isNotEmpty() && color.isNotEmpty()) {
                    val params =
                        PostSubjectUseCase.Params(title, color)
                    _isSuccess.value = Event(postSubjectUseCase.buildParamsUseCaseSuspend(params))
                } else {
                    _isFailure.value = Event("빈 값이 없는지 확인해주세요.")
                }
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message?:"")
            }
        }
    }

    fun modifySubject() {
        val oldTitle = oldTitle.value?:""
        val title = title.value?:""
        val color = color.value?:""

        viewModelScope.launch {
            try {
                val params =
                    PutSubjectUseCase.Params(oldTitle, title, color)
                _isSuccess.value = Event(putSubjectUseCase.buildParamsUseCaseSuspend(params))
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message?:"")
            }
        }
    }
}