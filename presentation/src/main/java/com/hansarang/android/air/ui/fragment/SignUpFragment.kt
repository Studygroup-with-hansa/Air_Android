package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.FileUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.hansarang.android.air.databinding.FragmentSignUpBinding
import com.hansarang.android.air.ui.activity.MainActivity
import com.hansarang.android.air.ui.extention.asMultipart
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.fragment.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var launcher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.btnAddImageSignUp.setOnClickListener {
            launcher.launch("image/*")
        }
    }

    private fun init() {
        launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                binding.ivProfileSignUp.load(it)
                with(requireContext()) {
                    viewModel.image =
                        it.asMultipart(
                            "profileImage",
                            cacheDir,
                            contentResolver
                        )!!
                }
            }
        }

        with(viewModel) {
            nickname.observe(viewLifecycleOwner) {
                val regex = Regex("^[ㄱ-ㅎ가-힣]*\$")
                finishButtonEnabled.value = it.length in 2..8 || regex.matches(it)
                binding.tilNicknameSignUp.error =
                    if (it.length !in 2..8 || it.isEmpty()) {
                        "닉네임은 두자 이상 8자 이내로 입력해 주세요."
                    } else if (!regex.matches(it)) {
                        "닉네임은 한글만 입력 가능합니다."
                    } else {
                        ""
                    }
            }

            isSuccess.observe(viewLifecycleOwner, EventObserver {
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            })

            isFailure.observe(viewLifecycleOwner, EventObserver {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

}