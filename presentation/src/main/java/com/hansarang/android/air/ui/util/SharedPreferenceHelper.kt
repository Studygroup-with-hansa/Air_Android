package com.hansarang.android.air.ui.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceHelper {
    lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE)
    }

    var token: String
        get() = sharedPreferences.getString("token", "")!!
        set(value) {
            val edit = sharedPreferences.edit()
            edit.putString(token, value)
            edit.apply()
        }
}