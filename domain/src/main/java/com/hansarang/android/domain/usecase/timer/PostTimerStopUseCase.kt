package com.hansarang.android.domain.usecase.timer

import com.hansarang.android.domain.base.BaseParamsUseCase
import com.hansarang.android.domain.repository.TimerRepository
import javax.inject.Inject

class PostTimerStopUseCase @Inject constructor(
    private val timerRepository: TimerRepository
): BaseParamsUseCase<PostTimerStopUseCase.Params, String>() {
    data class Params(
        val title: String
    )

    override suspend fun buildParamsUseCaseSuspend(params: Params): String {
        return timerRepository.postTimerStart(params.title)
    }
}