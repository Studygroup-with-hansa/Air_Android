package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSignUpBinding
import com.hansarang.android.air.ui.activity.MainActivity

class SignUpFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignInSignUp.setOnClickListener {
            navController.navigateUp()
        }

        binding.btnSubmitSignUp.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}