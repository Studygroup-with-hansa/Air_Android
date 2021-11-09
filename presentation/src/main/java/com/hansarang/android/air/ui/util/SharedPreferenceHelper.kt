package com.hansarang.android.air.ui.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceHelper {
    const val LIGHT_MODE = 0
    const val DARK_MODE = 1
    const val SYSTEM_MODE = 2

    lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    }

    var token: String
        get() = sharedPreferences.getString("token", "")!!
        set(value) {
            val edit = sharedPreferences.edit()
            edit.putString("token", value)
            edit.apply()
        }

    var nightMode: Int
        get() = sharedPreferences.getInt("nightMode", 0)
        set(value) {
            val edit = sharedPreferences.edit()
            edit.putInt("nightMode", value)
            edit.apply()
        }
}