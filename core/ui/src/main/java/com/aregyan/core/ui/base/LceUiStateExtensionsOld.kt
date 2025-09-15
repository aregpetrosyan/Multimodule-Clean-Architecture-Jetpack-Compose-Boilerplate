package com.aregyan.core.ui.base

import kotlinx.coroutines.flow.MutableStateFlow

// This extension helps update a MutableStateFlow of LceUiState
// It handles the type safety when setting Loading or Error states.
fun <T> MutableStateFlow<LceUiStateOld<T>>.setIdle() {
    value = LceUiStateOld.Idle
}

fun <T> MutableStateFlow<LceUiStateOld<T>>.setLoading() {
    val lastData = (value as? LceUiStateOld.Success)?.data
    value = LceUiStateOld.Loading(previousData = lastData)
}

fun <T> MutableStateFlow<LceUiStateOld<T>>.setSuccess(data: T) {
    value = LceUiStateOld.Success(data)
}

fun <T> MutableStateFlow<LceUiStateOld<T>>.setError(throwable: Throwable) {
    val lastData = (value as? LceUiStateOld.Success)?.data
    value = LceUiStateOld.Error(
        throwable = throwable,
        previousData = lastData
    )
}

inline fun <T> LceUiStateOld<T>.updateSuccess(
    transform: (T) -> T
): LceUiStateOld<T> {
    return when (this) {
        is LceUiStateOld.Success -> this.copy(data = transform(this.data))
        else -> this
    }
}