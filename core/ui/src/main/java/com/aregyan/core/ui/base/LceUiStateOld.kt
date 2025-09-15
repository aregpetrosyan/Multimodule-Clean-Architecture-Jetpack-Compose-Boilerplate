package com.aregyan.core.ui.base

import androidx.compose.runtime.Immutable

@Immutable
sealed class LceUiStateOld<out T> : UiState {

    object Idle : LceUiStateOld<Nothing>()

    data class Loading<out T>(
        val previousData: T? = null
    ) : LceUiStateOld<T>()

    data class Success<out T>(
        val data: T
    ) : LceUiStateOld<T>()

    data class Error<out T>(
        val throwable: Throwable,
        val previousData: T? = null
    ) : LceUiStateOld<T>()
}