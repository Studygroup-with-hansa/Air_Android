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
            Stats("2021.09.15", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 15000),
            Stats("2021.09.16", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 200),
            Stats("2021.09.17", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 10),
            Stats("2021.09.18", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 0),
            Stats("2021.09.19", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
            Stats("2021.09.20", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 20000),
            Stats("2021.09.21", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 10000),
        )
    }
}
