package com.hansarang.android.air.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSignInBinding
import com.hansarang.android.air.ui.activity.MainActivity
import com.hansarang.android.air.ui.livedata.EventObserver
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.token
import com.hansarang.android.air.ui.viewmodel.fragment.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val navController by lazy { findNavController() }
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()
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
        }.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
    }

    private fun observe() = with(viewModel) {
        email.observe(viewLifecycleOwner) {
            val emailChk = Patterns.EMAIL_ADDRESS
            binding.tilEmailSignIn.error =
                if (!emailChk.matcher(it).matches()) {
                    resources.getString(R.string.please_set_validate_email)
                } else {
                    ""
                }
        }

        authCode.observe(viewLifecycleOwner) {
            signInButtonEnabled.value = it.isNotEmpty()
        }

        isFailure.observe(viewLifecycleOwner, EventObserver {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        isEmailSent.observe(viewLifecycleOwner) {
            if (it == true && binding.tvCountdownTimer.text != "시간 초과")
                countDownTimer.start()
        }

        isAuthSuccess.observe(viewLifecycleOwner) {
            token = "Token $it"
            Log.d("SignInViewModel", "postSendAuthCode: ${token}")
            if (isEmailExist.value == true) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                navController.navigate(R.id.action_signInFragment_to_signUpFragment)
            }
        }
    }

}