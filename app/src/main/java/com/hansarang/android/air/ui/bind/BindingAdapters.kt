package com.hansarang.android.air.ui.bind

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setProgressBar")
fun ProgressBar.setProgressBar(progressValue: Int) {
    ObjectAnimator.ofInt(this, "progress", progressValue)
        .setDuration(500)
        .start()
}

@BindingAdapter("setDateTime", "setPattern")
fun TextView.setDateTime(timeInMillis: Long, pattern: String) {
    val sdf = SimpleDateFormat(pattern, Locale.KOREA)
    text = sdf.format(Date(timeInMillis))
}

@BindingAdapter("setTime", "setPattern")
fun TextView.setTime(timeInMillis: Long, pattern: String) {
    var sec = timeInMillis
    var min = sec / 60
    val hour = min / 60
    sec %= 60
    min %= 60

    text = String.format(pattern, hour, min, sec)
}

@BindingAdapter("app:backgroundTint")
fun FloatingActionButton.setTint(colorCode: String) {
    supportBackgroundTintList = ColorStateList.valueOf(Color.parseColor(colorCode))
}