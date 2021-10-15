package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.fragment.SignInViewModel
import com.hansarang.android.domain.usecase.user.PostRequestAuthUseCase
import com.hansarang.android.domain.usecase.user.PutSendAuthCodeUseCase

class SignInViewModelFactory(
    private val postRequestAuthUseCase: PostRequestAuthUseCase,
    private val putSendAuthCodeUseCase: PutSendAuthCodeUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            SignInViewModel(postRequestAuthUseCase, putSendAuthCodeUseCase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}