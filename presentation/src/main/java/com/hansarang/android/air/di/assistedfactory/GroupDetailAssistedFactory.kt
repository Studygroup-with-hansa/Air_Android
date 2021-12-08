package com.hansarang.android.air.di.assistedfactory

import com.hansarang.android.air.ui.viewmodel.fragment.GroupDetailViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface GroupDetailAssistedFactory {
    fun create(
        @Assisted("groupCode") groupCode: String,
        @Assisted("leader") leader: String,
        @Assisted("leaderEmail") leaderEmail: String
    ): GroupDetailViewModel
}