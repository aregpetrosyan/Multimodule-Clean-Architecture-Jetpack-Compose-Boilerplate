package com.aregyan.core.ui.base

import kotlinx.coroutines.flow.MutableStateFlow

// This extension helps update a MutableStateFlow of LceUiState
// It handles the type safety when setting Loading or Error states.
fun <T> MutableStateFlow<LceUiState<T>>.setIdle() {
    value = LceUiState.Idle
}

fun <T> MutableStateFlow<LceUiState<T>>.setLoading() {
    val lastData = (value as? LceUiState.Success)?.data
    value = LceUiState.Loading(previousData = lastData)
}

fun <T> MutableStateFlow<LceUiState<T>>.setSuccess(data: T) {
    value = LceUiState.Success(data)
}

fun <T> MutableStateFlow<LceUiState<T>>.setError(throwable: Throwable) {
    val lastData = (value as? LceUiState.Success)?.data
    value = LceUiState.Error(
        throwable = throwable,
        previousData = lastData
    )
}

inline fun <T> LceUiState<T>.updateSuccess(
    transform: (T) -> T
): LceUiState<T> {
    return when (this) {
        is LceUiState.Success -> this.copy(data = transform(this.data))
        else -> this
    }
}