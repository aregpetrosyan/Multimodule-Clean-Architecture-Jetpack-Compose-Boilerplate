package com.aregyan.core.ui.base

/** Safely update the data only if the state is Success */
inline fun <T> LceUiState<T>.updateSuccess(
    transform: (T) -> T
): LceUiState<T> = if (isSuccess && data != null) {
    LceUiState.success(transform(data))
} else {
    this
}