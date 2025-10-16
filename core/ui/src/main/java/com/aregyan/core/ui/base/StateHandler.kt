package com.aregyan.core.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> StateHandler(
    state: LceUiState<T>,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
    idleContent: @Composable () -> Unit = {},
    loadingContent: @Composable () -> Unit = {
        LoadingScreen(modifier)
    },
    errorContent: @Composable (throwable: Throwable) -> Unit = { throwable ->
        ErrorScreen(
            errorMessage = throwable.message.orEmpty(),
            onRetry = onRetry ?: {}
        )
    },
    successContent: @Composable (T) -> Unit,
) {
    when {
        state.isLoading -> {
            loadingContent()
        }

        state.isError -> {
            state.error?.let { errorContent(it) }
        }

        state.isSuccess -> {
            state.data?.let { successContent(it) }
        }

        state.isIdle -> {
            idleContent()
        }
    }
}
