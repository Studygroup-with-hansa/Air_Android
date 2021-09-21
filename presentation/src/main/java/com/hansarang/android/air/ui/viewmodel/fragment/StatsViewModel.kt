package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hansarang.android.domain.entity.dto.Stats
import com.hansarang.android.domain.entity.dto.Subject

class StatsViewModel(

): ViewModel() {
    private val _stats = MutableLiveData<ArrayList<Stats>>()
    val stats: LiveData<ArrayList<Stats>> = _stats
    fun getStats() {
        _stats.value = arrayListOf(
            Stats("2021.09.15", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.16", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.17", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.18", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.19", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.20", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.21", 10000, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
        )
    }
}
