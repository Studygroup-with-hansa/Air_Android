package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.fragment.TodoViewModel
import com.hansarang.android.domain.usecase.checklist.GetCheckListUseCase

class TodoViewModelFactory(
    private val getCheckListUseCase: GetCheckListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(getCheckListUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}