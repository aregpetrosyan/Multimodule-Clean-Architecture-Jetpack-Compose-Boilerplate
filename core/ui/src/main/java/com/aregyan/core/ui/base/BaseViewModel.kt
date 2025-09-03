package com.aregyan.core.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

interface UiIntent
interface UiState
interface UiEvent

interface RetryIntentMarker
interface SystemIntentMarker

abstract class BaseViewModel<I : UiIntent, S : UiState> : ViewModel() {

    protected var _state: MutableStateFlow<S>
    var state: StateFlow<S>
        private set

    protected val _navigationEvents = MutableSharedFlow<UiEvent>()
    val navigationEvents: SharedFlow<UiEvent> = _navigationEvents.asSharedFlow()

    init {
        _state = MutableStateFlow(createInitialState())
        state = _state.asStateFlow()
    }

    private var lastUserIntent: I? = null

    fun onIntent(intent: I) {
        when (intent) {
            is RetryIntentMarker -> {
                lastUserIntent?.let { handleIntent(it) }
            }

            is SystemIntentMarker -> {
                handleIntent(intent)
            }

            else -> {
                lastUserIntent = intent
                handleIntent(intent)
            }
        }
    }

    protected abstract fun createInitialState(): S
    protected abstract fun handleIntent(intent: I)
}