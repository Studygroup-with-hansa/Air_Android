package com.hansarang.android.air.ui.fragment

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.viewModels
import com.hansarang.android.air.databinding.FragmentSettingBinding
import com.hansarang.android.air.ui.base.BaseFragment
import com.hansarang.android.air.ui.dialog.DialogAlertFragment
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.DARK_MODE
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.LIGHT_MODE
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.SYSTEM_MODE
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.nightMode
import com.hansarang.android.air.ui.util.SharedPreferenceHelper.token
import com.hansarang.android.air.ui.viewmodel.fragment.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModels()

    override fun observerViewModel() {

        with(viewModel) {
            lightMode.value = (nightMode == LIGHT_MODE)
            darkMode.value = (nightMode == DARK_MODE)
            systemMode.value = (nightMode == SYSTEM_MODE)
        }

        viewModel.logoutButtonClick.observe(viewLifecycleOwner) {
            val alert = DialogAlertFragment.newInstance("알림", "로그아웃 하시겠습니까?", "취소", "확인")
            alert.show(parentFragmentManager, "logoutAlert")

            alert.setOnNegativeButtonClickListener {
                token = ""
                requireActivity().finish()
            }
        }

        viewModel.lightMode.observe(viewLifecycleOwner) {
            if (it) {
                nightMode = LIGHT_MODE
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }

        viewModel.darkMode.observe(viewLifecycleOwner) {
            if (it) {
                nightMode = DARK_MODE
                setDefaultNightMode(MODE_NIGHT_YES)
            }
        }

        viewModel.systemMode.observe(viewLifecycleOwner) {
            if (it) {
                nightMode = SYSTEM_MODE
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

}