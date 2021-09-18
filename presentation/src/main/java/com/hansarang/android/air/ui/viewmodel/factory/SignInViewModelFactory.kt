package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.fragment.SignInViewModel
import com.hansarang.android.domain.usecase.user.GetRequestAuthUseCase
import com.hansarang.android.domain.usecase.user.PostSendAuthCodeUseCase

class SignInViewModelFactory(
    private val getRequestAuthUseCase: GetRequestAuthUseCase,
    private val postSendAuthCodeUseCase: PostSendAuthCodeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            SignInViewModel(getRequestAuthUseCase, postSendAuthCodeUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}