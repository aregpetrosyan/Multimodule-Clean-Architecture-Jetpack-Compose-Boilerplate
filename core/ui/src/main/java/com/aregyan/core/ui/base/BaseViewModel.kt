package com.aregyan.core.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/** Marker interfaces for MVI **/
interface UiIntent       // Represents an action/event from the UI
interface UiState        // Represents the screen's state
interface UiEvent        // Represents one-time events (like navigation or toast messages)

interface RetryIntentMarker      // Marks intents that are "retry" actions
interface SystemIntentMarker     // Marks intents that are results of side-effects, not triggered by the UI

/**
 * BaseViewModel implementing a unidirectional MVI pattern.
 * I = type of Intent
 * T = type of the data within LceUiState
 */
abstract class BaseViewModel<I : UiIntent, T : Any> : ViewModel() {

    /**
     * Backing mutable state flow holding the current UI state.
     * Initialized with a default Idle state.
     */
    protected val _state: MutableStateFlow<LceUiState<T>> = MutableStateFlow(LceUiState.idle())

    /** Public read-only state flow for observing state in the UI. */
    val state: StateFlow<LceUiState<T>> = _state.asStateFlow()

    /** Shared flow for one-time events like navigation or snackbars. */
    protected val _navigationEvents = MutableSharedFlow<UiEvent>()
    val navigationEvents: SharedFlow<UiEvent> = _navigationEvents.asSharedFlow()

    /** Last user-triggered intent (for Retry support). */
    private var lastUserIntent: I? = null

    /**
     * Entry point for all intents.
     *
     * MVI rules:
     * - UI only calls onIntent() (no direct state mutation).
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
            is RetryIntentMarker -> lastUserIntent?.let { handleIntent(it) }
            is SystemIntentMarker -> handleIntent(intent)
            else -> {
                lastUserIntent = intent
                handleIntent(intent)
            }
        }
    }

    /** Handle side-effects triggered by the intent. Must be implemented by each ViewModel. */
    protected abstract fun handleIntent(intent: I)

    /** Pure function that reduces current state + intent into a new state. Must be implemented by each ViewModel. */
    protected abstract fun reduce(currentState: LceUiState<T>, intent: I): LceUiState<T>
}