package com.aregyan.core.ui.base

import androidx.compose.runtime.Immutable

@Immutable
data class LceUiState<out T>(
    val data: T? = null,
    val error: Throwable? = null,
    val type: Type
) : UiState {

    enum class Type { Idle, Loading, Success, Error }

    val isIdle: Boolean get() = type == Type.Idle
    val isLoading: Boolean get() = type == Type.Loading
    val isSuccess: Boolean get() = type == Type.Success
    val isError: Boolean get() = type == Type.Error

    companion object {
        fun <T> idle(): LceUiState<T> =
            LceUiState(type = Type.Idle)

        fun <T> loading(): LceUiState<T> =
            LceUiState(type = Type.Loading)

        fun <T> success(data: T): LceUiState<T> =
            LceUiState(data = data, type = Type.Success)

        fun <T> error(throwable: Throwable): LceUiState<T> =
            LceUiState(error = throwable, type = Type.Error)
    }
}