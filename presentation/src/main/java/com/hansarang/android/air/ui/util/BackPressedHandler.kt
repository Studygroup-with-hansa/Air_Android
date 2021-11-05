package com.hansarang.android.air.ui.util

import android.app.Activity
import android.widget.Toast

class BackPressedHandler(private val activity: Activity) {
    private var backPressedTime = 0L
    fun onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(activity.applicationContext, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            activity.finish()
        }
    }
}