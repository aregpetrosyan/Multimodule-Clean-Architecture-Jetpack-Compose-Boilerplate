package com.aregyan.core.ui.base

import kotlinx.coroutines.flow.MutableStateFlow

/** Extension helpers for MutableStateFlow of LceUiState **/

fun <T> MutableStateFlow<LceUiState<T>>.setIdle() {
    value = LceUiState.idle()
}

fun <T> MutableStateFlow<LceUiState<T>>.setLoading() {
    value = LceUiState.loading()
}

fun <T> MutableStateFlow<LceUiState<T>>.setSuccess(data: T) {
    value = LceUiState.success(data)
}

fun <T> MutableStateFlow<LceUiState<T>>.setError(throwable: Throwable) {
    value = LceUiState.error(throwable)
}

/** Safely update the data only if the state is Success */
inline fun <T> LceUiState<T>.updateSuccess(
    transform: (T) -> T
): LceUiState<T> = if (isSuccess && data != null) {
    LceUiState.success(transform(data))
} else {
    this
}