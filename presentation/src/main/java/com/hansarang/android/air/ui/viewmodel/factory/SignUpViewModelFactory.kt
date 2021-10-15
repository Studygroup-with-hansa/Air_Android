package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.fragment.SignUpViewModel
import com.hansarang.android.domain.usecase.user.PutModifyUsernameUseCase

class SignUpViewModelFactory(
    private val putModifyUsernameUseCase: PutModifyUsernameUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(putModifyUsernameUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}