package com.hansarang.android.air.ui.viewmodel.fragment

import android.util.Log
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
    var isFirstLoad = true

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Event<String>>()
    val isFailure: LiveData<Event<String>> = _isFailure

    private val _stats = MutableLiveData<ArrayList<Stats>>()
    val stats: LiveData<ArrayList<Stats>> = _stats

    fun getStats() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val decimalFormat = DecimalFormat("00")
                val currentDate = Calendar.getInstance(Locale.KOREA)
                val endDate =
                    "${currentDate.get(Calendar.YEAR)}" +
                            "-${decimalFormat.format(currentDate.get(Calendar.MONTH) + 1)}" +
                            "-${decimalFormat.format(currentDate.get(Calendar.DATE))}"

                currentDate.add(Calendar.DAY_OF_MONTH, -6)
                val startDate =
                    "${currentDate.get(Calendar.YEAR)}" +
                            "-${decimalFormat.format(currentDate.get(Calendar.MONTH) + 1)}" +
                            "-${decimalFormat.format(currentDate.get(Calendar.DATE))}"

                Log.d("Stats", "getStats: $startDate $endDate")

                val params = GetWeeklyStatsUseCase.Params(startDate, endDate)
                _stats.value = ArrayList(getWeeklyStatsUseCase.buildParamsUseCaseSuspend(params).stats)
            } catch (e: Throwable) {
                _isFailure.value = Event(e.message?:"")
            } finally {
                isFirstLoad = false
                _isLoading.value = false
            }
        }
    }
}
