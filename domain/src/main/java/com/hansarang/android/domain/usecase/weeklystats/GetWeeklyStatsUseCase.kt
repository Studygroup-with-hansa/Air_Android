package com.hansarang.android.domain.usecase.weeklystats

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.entity.dto.BaseStats
import com.hansarang.android.domain.repository.WeeklyStatsRepository
import javax.inject.Inject

class GetWeeklyStatsUseCase @Inject constructor(
    private val weeklyStatsRepository: WeeklyStatsRepository
): BaseParamsUseCase<GetWeeklyStatsUseCase.Params, BaseStats>() {
    data class Params(
        val startDate: String,
        val endDate: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): BaseStats {
        return weeklyStatsRepository.getStats(params.startDate, params.endDate)
    }
}