package com.hansarang.android.air.ui.viewmodel.fragment

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansarang.android.air.ui.livedata.Event
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
    val progressBarVisibility = MutableLiveData(View.GONE)

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _stats = MutableLiveData<ArrayList<Stats>>()
    val stats: LiveData<ArrayList<Stats>> = _stats

    fun getStats() {
        progressBarVisibility.value = View.VISIBLE

        viewModelScope.launch {
            try {
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
                _stats.value = ArrayList(getWeeklyStatsUseCase.buildParamsUseCaseSuspend(params).stats)
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message?:"")
            } finally {
                progressBarVisibility.value = View.GONE
            }
        }
    }
}
