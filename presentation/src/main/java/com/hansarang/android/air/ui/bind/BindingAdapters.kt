package com.hansarang.android.air.ui.bind

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageButton
import android.widget.ImageView
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

fun ImageView.setDefaultToggle(enabled: Boolean) {
    this.rotation =
        if (enabled) 180f
        else 0f
}

fun ImageView.setToggleEnabled(enabled: Boolean) {
    if (enabled) {
        animate().setDuration(200).rotation(180f)
    }
    else {
        animate().setDuration(200).rotation(0f)
    }
}

fun View.setExpend() {
    visibility = View.VISIBLE
    with(layoutParams) {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}

fun View.setCollapse() {
    visibility = View.GONE
}

fun View.expandAnimation() {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val actualHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            layoutParams.height = if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT
            else (actualHeight * interpolatedTime).toInt()

            requestLayout()
        }
    }

    animation.duration = (actualHeight / context.resources.displayMetrics.density).toLong()

    startAnimation(animation)
}

fun View.collapseAnimation() {
    val actualHeight = measuredHeight

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) {
                visibility = View.GONE
            } else {
                layoutParams.height = (actualHeight - (actualHeight * interpolatedTime)).toInt()
                requestLayout()
            }
        }
    }

    animation.duration = (actualHeight / context.resources.displayMetrics.density).toLong()
    startAnimation(animation)
}