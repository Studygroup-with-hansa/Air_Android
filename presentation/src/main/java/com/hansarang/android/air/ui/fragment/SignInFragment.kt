package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSignInBinding
import com.hansarang.android.air.ui.activity.MainActivity

class SignInFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmitSignIn.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

}