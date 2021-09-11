package com.hansarang.android.air.ui.bind

import android.animation.ObjectAnimator
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setProgressBar")
fun ProgressBar.setProgressBar(progressValue: Int) {
    ObjectAnimator.ofInt(this, "progress", progressValue)
        .setDuration(500)
        .start()
}

@BindingAdapter("setTime", "setPattern")
fun TextView.setTime(timeInMillis: Long, pattern: String) {
    val sdf = SimpleDateFormat(pattern, Locale.KOREA)
    text = sdf.format(Date(timeInMillis))
}