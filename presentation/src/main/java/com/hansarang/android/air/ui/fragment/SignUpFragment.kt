package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import coil.load
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSignUpBinding
import com.hansarang.android.air.ui.activity.MainActivity

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.btnSubmitSignUp.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.fabAddImageSignUp.setOnClickListener {
            launcher.launch("image/*")
        }
    }

    private fun init() {
        launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.ivProfileSignUp.load(it)
            }
        }
    }

}