package com.aregyan.core.ui.base

import kotlinx.coroutines.flow.MutableStateFlow

// This extension helps update a MutableStateFlow of LceUiState
// It handles the type safety when setting Loading or Error states.
fun <T> MutableStateFlow<LceUiState<T>>.setLoading() {
    this.value = LceUiState.Loading as LceUiState<T> // Safe cast due to 'out T'
}

fun <T> MutableStateFlow<LceUiState<T>>.setSuccess(data: T) {
    this.value = LceUiState.Success(data)
}

fun <T> MutableStateFlow<LceUiState<T>>.setError(throwable: Throwable) {
    this.value = LceUiState.Error(throwable) as LceUiState<T> // Safe cast due to 'out T'
}

fun <T> MutableStateFlow<LceUiState<T>>.setIdle() {
    this.value = LceUiState.Idle as LceUiState<T> // Safe cast due to 'out T'
}