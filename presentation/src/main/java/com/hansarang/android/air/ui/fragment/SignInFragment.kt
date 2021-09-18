package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSignInBinding
import com.hansarang.android.air.ui.viewmodel.factory.SignInViewModelFactory
import com.hansarang.android.air.ui.viewmodel.fragment.SignInViewModel
import com.hansarang.android.domain.usecase.user.GetRequestAuthUseCase
import com.hansarang.android.domain.usecase.user.PostSendAuthCodeUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class SignInFragment : Fragment() {

    @Inject
    lateinit var getRequestAuthUseCase: GetRequestAuthUseCase

    @Inject
    lateinit var postSendAuthCodeUseCase: PostSendAuthCodeUseCase

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)

        val factory = SignInViewModelFactory(getRequestAuthUseCase, postSendAuthCodeUseCase)
        viewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmitSignIn.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

}