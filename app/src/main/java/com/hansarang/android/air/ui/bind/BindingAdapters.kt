package com.hansarang.android.air.ui.bind

import android.animation.ObjectAnimator
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter

@BindingAdapter("setProgressBar")
fun ProgressBar.setProgressBar(progressValue: Int) {
    ObjectAnimator.ofInt(this, "progress", progressValue)
        .setDuration(500)
        .start()
}