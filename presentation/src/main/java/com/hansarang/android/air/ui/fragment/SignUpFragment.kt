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
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.hansarang.android.air.databinding.FragmentSignUpBinding
import com.hansarang.android.air.ui.activity.MainActivity
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.extention.asMultipart
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.viewmodel.fragment.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>() {

    override val viewModel: SignUpViewModel by viewModels()
    private val launcher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
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

    override fun observerViewModel() {
        binding.etNicknameSignUp.doAfterTextChanged {
            val regex = Regex("^[ㄱ-ㅎ가-힣]*\$")
            val text = it.toString()
            binding.btnSubmitSignUp.isEnabled = text.length in 2..8 || regex.matches(text)
            binding.tilNicknameSignUp.error =
                if (text.length !in 2..8 || text.isEmpty()) {
                    "닉네임은 두자 이상 8자 이내로 입력해 주세요."
                } else if (!regex.matches(text)) {
                    "닉네임은 한글만 입력 가능합니다."
                } else {
                    ""
                }
        }

        viewModel.addImageButtonClick.observe(viewLifecycleOwner) {
            launcher.launch("image/*")
        }

        viewModel.isSuccess.observe(viewLifecycleOwner, EventObserver {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })

        viewModel.isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }


}