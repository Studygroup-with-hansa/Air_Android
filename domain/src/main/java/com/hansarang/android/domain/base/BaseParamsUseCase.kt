package com.hansarang.android.domain.base

abstract class BaseParamsUseCase<Params, T> {
    abstract suspend fun buildParamsUseCaseSuspend(params: Params): T
}