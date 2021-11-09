package com.hansarang.android.air.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.hansarang.android.air.R
import com.hansarang.android.air.databinding.FragmentSettingBinding
import com.hansarang.android.air.ui.dialog.DialogAlertFragment
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.token
import com.hansarang.android.air.ui.viewmodel.fragment.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModels()
    private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(inflater)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() = with(binding) {
        btnLogoutSetting.setOnClickListener {
            val alert = DialogAlertFragment.newInstance("알림", "로그아웃 하시겠습니까?", "취소", "확인")
            alert.show(parentFragmentManager, "logoutAlert")

            alert.setOnNegativeButtonClickListener {
                token = ""
                requireActivity().finish()
            }
        }
    }

}