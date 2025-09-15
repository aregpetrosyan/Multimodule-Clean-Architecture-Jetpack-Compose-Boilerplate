package com.aregyan.core.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> StateHandlerOld(
    state: LceUiStateOld<T>,
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
        is LceUiStateOld.Loading -> {
            loadingContent(state.previousData)
        }

        is LceUiStateOld.Error -> {
            errorContent(state.throwable, state.previousData)
        }

        is LceUiStateOld.Success -> {
            successContent(state.data)
        }

        is LceUiStateOld.Idle -> {
            idleContent()
        }
    }
}



