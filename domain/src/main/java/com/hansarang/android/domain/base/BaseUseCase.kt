package com.hansarang.android.domain.base

abstract class BaseUseCase<T> {
    abstract suspend fun buildUseCaseSuspend(): T
}