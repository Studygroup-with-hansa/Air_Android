package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSignInBinding
import com.hansarang.android.air.ui.activity.MainActivity
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.token
import com.hansarang.android.air.ui.viewmodel.fragment.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    override val viewModel: SignInViewModel by viewModels()

    private val countDownTimer by lazy {
        object : CountDownTimer((5 * 60 * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvCountdownTimer.visibility =
                    if (viewModel.isEmailSent.value == false) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                val millis = millisUntilFinished / 1000
                val minute = String.format("%02d", millis / 60)
                val second = String.format("%02d", millis % 60)
                val time = "$minute:$second"
                binding.tvCountdownTimer.text = time
            }

            override fun onFinish() {
                binding.tvCountdownTimer.text = "시간 초과"
            }
        }
    }
    override fun observerViewModel() {
        binding.etEmailSignIn.doAfterTextChanged {
            val emailChk = Patterns.EMAIL_ADDRESS
            binding.tilEmailSignIn.error =
                if (!emailChk.matcher(it.toString()).matches()) {
                    resources.getString(R.string.please_set_validate_email)
                } else {
                    ""
                }
        }

        binding.etValidateCodeSignIn.doAfterTextChanged {
            binding.btnSubmitSignIn.isEnabled = it.toString().isNotEmpty()
        }

        viewModel.isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.isEmailSent.observe(viewLifecycleOwner) {
            if (it == true)
                countDownTimer.start()
        }

        viewModel.isAuthSuccess.observe(viewLifecycleOwner) {
            token = "Token $it"
            Log.d("SignInViewModel", "postSendAuthCode: ${token}")
            if (viewModel.isEmailExist.value == true) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

}