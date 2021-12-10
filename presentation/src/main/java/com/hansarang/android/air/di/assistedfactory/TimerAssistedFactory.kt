package com.hansarang.android.air.di.assistedfactory

import com.hansarang.android.air.ui.viewmodel.activity.TimerViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface TimerAssistedFactory {
    fun create(
        @Assisted("title") title: String,
        @Assisted("date") date: Long,
        @Assisted("time") time: Long,
        @Assisted("goal") goal: Long,
        @Assisted("isStarted") isStarted: Boolean
    ): TimerViewModel
}