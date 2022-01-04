package com.hansarang.android.air.ui.viewmodel.adapter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
import com.hansarang.android.domain.entity.dto.CheckListItem
import com.hansarang.android.domain.usecase.checklist.DeleteCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PostCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutModifyCheckListUseCase
import com.hansarang.android.domain.usecase.checklist.PutStatusChangeCheckListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class TodoListAdapterViewModel @Inject constructor(
    private val postCheckListUseCase: PostCheckListUseCase,
    private val putModifyCheckListUseCase: PutModifyCheckListUseCase,
    private val putStatusChangeCheckListUseCase: PutStatusChangeCheckListUseCase,
    private val deleteCheckListUseCase: DeleteCheckListUseCase
): ViewModel() {

    val percents = MutableLiveData<String>()

    private val _isPostCheckListSuccess = MutableLiveData<Event<CheckListItem>>()
    val isPostCheckListSuccess: LiveData<Event<CheckListItem>> = _isPostCheckListSuccess

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    var date = System.currentTimeMillis()

    fun postCheckList(subject: String, date: String, todo: String) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = PostCheckListUseCase.Params(subject, date, todo)
                    val pk = postCheckListUseCase.buildParamsUseCaseSuspend(params).pk
                    _isPostCheckListSuccess.value = Event(CheckListItem(pk, todo))
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            }
        }
    }

    fun deleteCheckList(pk: Int, todo: String) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = DeleteCheckListUseCase.Params(pk)
                    deleteCheckListUseCase.buildParamsUseCaseSuspend(params)
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            }
        }
    }

    fun putCheckList(pk: Int, todo: String) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = PutModifyCheckListUseCase.Params(pk, todo)
                    putModifyCheckListUseCase.buildParamsUseCaseSuspend(params)
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            } finally {
                Log.d("TodoViewModel", "putCheckList: ")
            }
        }
    }

    fun putStatusChangeCheckList(checkListItem: CheckListItem) {
        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    val params = PutStatusChangeCheckListUseCase.Params(checkListItem.pk)
                    putStatusChangeCheckListUseCase.buildParamsUseCaseSuspend(params)
                }
            } catch (e: TimeoutCancellationException) {
                _isFailure.value = Event("시간 초과")
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message ?: "")
            }
        }
    }
}