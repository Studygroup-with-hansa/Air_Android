package com.hansarang.android.air.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hansarang.android.air.ui.viewmodel.fragment.SignUpViewModel

class SignUpViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel() as T
        } else {
            throw IllegalArgumentException()
        }
    }
}