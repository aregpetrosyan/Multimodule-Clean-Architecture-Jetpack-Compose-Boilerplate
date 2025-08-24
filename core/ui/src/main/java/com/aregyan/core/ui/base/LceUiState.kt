package com.aregyan.core.ui.base

import androidx.compose.runtime.Immutable

@Immutable
sealed class LceUiState<out T> : UiState {
    object Idle : LceUiState<Nothing>()

    object Loading : LceUiState<Nothing>()

    @Immutable
    data class Success<out T>(val data: T) : LceUiState<T>()

    @Immutable
    data class Error(
        val throwable: Throwable
    ) : LceUiState<Nothing>()
}