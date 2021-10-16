package com.hansarang.android.air.ui.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.domain.entity.dto.Stats
import com.hansarang.android.domain.usecase.weeklystats.GetWeeklyStatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val getWeeklyStatsUseCase: GetWeeklyStatsUseCase
): ViewModel() {
    private val _stats = MutableLiveData<ArrayList<Stats>>()
    val stats: LiveData<ArrayList<Stats>> = _stats

    fun getStats() {

        viewModelScope.launch {
            val decimalFormat = DecimalFormat("00")
            val currentDate = Calendar.getInstance(Locale.KOREA)
            val endDate =
                "${currentDate.get(Calendar.YEAR)}" +
                        "-${decimalFormat.format(currentDate.get(Calendar.MONTH) + 1)}" +
                        "-${decimalFormat.format(currentDate.get(Calendar.DATE))}"

            val startDate =
                "${currentDate.get(Calendar.YEAR)}" +
                        "-${decimalFormat.format(currentDate.get(Calendar.MONTH) + 1)}" +
                        "-${decimalFormat.format(currentDate.get(Calendar.DATE))}"

            val params = GetWeeklyStatsUseCase.Params(startDate, endDate)
            _stats.value = ArrayList(getWeeklyStatsUseCase.buildParamsUseCaseSuspend(params))
        }

//        _stats.value = arrayListOf(
//            Stats("2021.09.15", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 15000),
//            Stats("2021.09.16", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 200),
//            Stats("2021.09.17", 40312, listOf(
//                Subject("국어", 30, "#ED6A5E"),
//                Subject("수학", 30, "#8886FF"),
//                Subject("영어", 30, "#F6C343"),
//                Subject("기타", 10, "#C7C7C7")
//            ), 10),
//            Stats("2021.09.18", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 0),
//            Stats("2021.09.19", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 40312),
//            Stats("2021.09.20", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 20000),
//            Stats("2021.09.21", 40312, listOf(Subject("국어", 10, "#ED6A5E")), 10000),
//        )
    }
}
