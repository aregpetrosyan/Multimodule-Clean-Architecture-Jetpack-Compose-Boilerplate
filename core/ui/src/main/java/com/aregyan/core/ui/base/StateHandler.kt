package com.aregyan.core.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> StateHandler(
    state: LceUiState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    idleContent: @Composable () -> Unit = {},
    loadingContent: @Composable (previousData: T?) -> Unit = {
        LoadingScreen(modifier)
    },
    errorContent: @Composable (throwable: Throwable, previousData: T?) -> Unit = { throwable, _ ->
        ErrorScreen(
            errorMessage = throwable.message.orEmpty(),
            onRetry = onRetry ?: {}
        )
    },
    successContent: @Composable (T) -> Unit,
) {
    when (state) {
        is LceUiState.Loading -> {
            loadingContent(state.previousData)
        }

        is LceUiState.Error -> {
            errorContent(state.throwable, state.previousData)
        }

        is LceUiState.Success -> {
            successContent(state.data)
        }

        is LceUiState.Idle -> {
            idleContent()
        }
    }
}



