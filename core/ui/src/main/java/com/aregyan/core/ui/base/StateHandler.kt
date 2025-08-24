package com.aregyan.core.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> StateHandler(
    state: LceUiState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    loadingContent: @Composable () -> Unit = { LoadingScreen(modifier) },
    errorContent: @Composable (Throwable) -> Unit = { throwable ->
        ErrorScreen(
            errorMessage = throwable.message.orEmpty(),
            onRetry = onRetry ?: {}
        )
    },
    idleContent: @Composable () -> Unit = {},
    successContent: @Composable (T) -> Unit,
) {
    when (state) {
        is LceUiState.Loading -> {
            loadingContent()
        }

        is LceUiState.Error -> {
            errorContent(state.throwable)
        }

        is LceUiState.Success -> {
            successContent(state.data)
        }

        is LceUiState.Idle -> {
            idleContent()
        }
    }
}



