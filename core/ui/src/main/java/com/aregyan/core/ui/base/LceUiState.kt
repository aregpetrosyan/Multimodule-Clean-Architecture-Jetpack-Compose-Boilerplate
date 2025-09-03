package com.aregyan.core.ui.base

import androidx.compose.runtime.Immutable

@Immutable
sealed class LceUiState<out T> : UiState {

    object Idle : LceUiState<Nothing>()

    data class Loading<out T>(
        val previousData: T? = null
    ) : LceUiState<T>()

    data class Success<out T>(
        val data: T
    ) : LceUiState<T>()

    data class Error<out T>(
        val throwable: Throwable,
        val previousData: T? = null
    ) : LceUiState<T>()
}