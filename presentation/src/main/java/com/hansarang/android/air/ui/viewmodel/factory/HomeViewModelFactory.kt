package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.fragment.HomeViewModel
import com.hansarang.android.domain.usecase.subject.DeleteSubjectUseCase
import com.hansarang.android.domain.usecase.subject.GetSubjectUseCase
import com.hansarang.android.domain.usecase.subject.PutSubjectUseCase

class HomeViewModelFactory(
    private val getSubjectUseCase: GetSubjectUseCase,
    private val deleteSubjectUseCase: DeleteSubjectUseCase,
    private val putSubjectUseCase: PutSubjectUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(getSubjectUseCase, deleteSubjectUseCase, putSubjectUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}