package com.aregyan.core.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * BaseViewModel implementing a unidirectional MVI pattern.
 * I = type of Intent
 * S = type of State
 */
abstract class BaseViewModelOld<I : UiIntent, S : UiState> : ViewModel() {

    /**
     * The backing mutable state flow holding the current UI state.
     * Initialized with a default Idle state.
     */
    protected val _state: MutableStateFlow<S> =
        MutableStateFlow(LceUiStateOld.Idle as S) // unchecked cast; safe if all states are LceUiState

    /**
     * Public read-only state flow for observing state in the UI.
     */
    val state: StateFlow<S> = _state.asStateFlow()

    /**
     * Shared flow for one-time events, such as navigation or snackbars.
     */
    protected val _navigationEvents = MutableSharedFlow<UiEvent>()
    val navigationEvents: SharedFlow<UiEvent> = _navigationEvents.asSharedFlow()

    /**
     * Keeps track of the last user-triggered intent.
     * Used to implement Retry functionality.
     */
    private var lastUserIntent: I? = null

    /**
     * Entry point for all intents.
     *
     * MVI rule:
     * - UI can only call onIntent() (no direct state mutation).
     * - System/result intents are emitted internally after side-effects.
     *
     * Retry mechanism:
     * - If the intent is marked as RetryIntentMarker, re-dispatch the last user intent.
     *
     * System intents:
     * - Emitted internally as results of side-effects (like network success/failure).
     * - They do not overwrite the last user intent.
     *
     * User intents:
     * - Any other intents from UI are stored as lastUserIntent.
     */
    fun onIntent(intent: I) {
        when (intent) {
            is RetryIntentMarker -> lastUserIntent?.let { handleIntent(it) } // replay last user intent
            is SystemIntentMarker -> handleIntent(intent)                   // just handle, don't save
            else -> {
                lastUserIntent = intent                                       // save user intent for retry
                handleIntent(intent)
            }
        }
    }

    /**
     * Handle side-effects triggered by the intent.
     * Each ViewModel must implement its own handling logic.
     */
    protected abstract fun handleIntent(intent: I)

    /**
     * Pure function that reduces current state + intent into a new state.
     * Must be implemented by each ViewModel.
     */
    protected abstract fun reduce(currentState: S, intent: I): S
}