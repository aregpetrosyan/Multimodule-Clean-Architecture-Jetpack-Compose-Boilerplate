package com.aregyan.feature.favorites

import androidx.lifecycle.viewModelScope
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiState
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.UiEvent
import com.aregyan.core.ui.base.UiIntent
import com.aregyan.core.ui.base.setIdle
import com.aregyan.core.ui.base.setSuccess
import com.aregyan.feature.favorites.api.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : BaseViewModel<FavoritesIntent, LceUiState<FavoriteState>>() {
    override fun createInitialState() = LceUiState.Idle

    override fun handleIntent(intent: FavoritesIntent) {
        when (intent) {
            FavoritesIntent.LoadFavorites -> loadFavorites()
            is FavoritesIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            is FavoritesIntent.OnPhotoClick -> onPhotoClick(intent.photo)
            is FavoritesIntent.OnSimilarClick -> onSimilarClick(intent.photo)
            else -> { /* No action required */
            }
        }
    }

    init {
        onIntent(FavoritesIntent.LoadFavorites)
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoritesUseCase.observeFavorites()
                .distinctUntilChanged()
                .collect {
                    if (it.isEmpty()) {
                        _state.setIdle()
                    } else {
                        _state.setSuccess(FavoriteState(it.toList()))
                    }
                }
        }
    }

    private fun onFavoriteClick(photo: Photo) {
        viewModelScope.launch {
            favoritesUseCase.toggleFavorite(photo)
        }
    }

    private fun onPhotoClick(photo: Photo?) {
        val currentState = state.value
        if (currentState is LceUiState.Success) {
            _state.setSuccess(currentState.data.copy(selectedPhoto = photo))
        }
    }

    private fun onSimilarClick(photo: Photo) {
        viewModelScope.launch {
            _navigationEvents.emit(FavoriteNavigationEvent.ShowSimilarPhotos(photo))
        }
    }
}

sealed class FavoritesIntent : UiIntent {
    object LoadFavorites : FavoritesIntent()
    data class OnFavoriteClick(val photo: Photo) : FavoritesIntent()
    data class OnPhotoClick(val photo: Photo?) : FavoritesIntent()
    data class OnSimilarClick(val photo: Photo) : FavoritesIntent()
    object Retry : FavoritesIntent(), RetryIntentMarker
}

sealed class FavoriteNavigationEvent : UiEvent {
    data class ShowSimilarPhotos(val photo: Photo) : FavoriteNavigationEvent()
}