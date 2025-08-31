package com.aregyan.feature.favorites

import androidx.lifecycle.viewModelScope
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiState
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.UiEvent
import com.aregyan.core.ui.base.UiIntent
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

    init {
        onIntent(FavoritesIntent.LoadFavorites)
    }

    override fun handleIntent(intent: FavoritesIntent) {
        _state.value = reduce(state.value, intent)

        when (intent) {
            FavoritesIntent.LoadFavorites -> loadFavorites()
            is FavoritesIntent.OnFavoriteClick -> toggleFavorite(intent.photo)
            is FavoritesIntent.OnSimilarClick -> navigateToSimilar(intent.photo)
            else -> { /* No side effects for other intents */
            }
        }
    }

    private fun reduce(
        currentState: LceUiState<FavoriteState>,
        intent: FavoritesIntent
    ): LceUiState<FavoriteState> = when (intent) {
        FavoritesIntent.LoadFavorites, FavoritesIntent.Retry -> LceUiState.Loading

        is FavoritesIntent.FavoritesLoaded ->
            if (intent.favorites.isEmpty()) LceUiState.Idle
            else LceUiState.Success(FavoriteState(intent.favorites.toList()))

        is FavoritesIntent.OnPhotoClick ->
            (currentState as? LceUiState.Success)?.copy(
                data = currentState.data.copy(selectedPhoto = intent.photo)
            ) ?: currentState

        else -> currentState
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoritesUseCase.observeFavorites()
                .distinctUntilChanged()
                .collect { favorites ->
                    onIntent(FavoritesIntent.FavoritesLoaded(favorites))
                }
        }
    }

    private fun toggleFavorite(photo: Photo) {
        viewModelScope.launch {
            favoritesUseCase.toggleFavorite(photo)
        }
    }

    private fun navigateToSimilar(photo: Photo) {
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
    data class FavoritesLoaded(val favorites: Set<Photo>) : FavoritesIntent()
    object Retry : FavoritesIntent(), RetryIntentMarker
}

sealed class FavoriteNavigationEvent : UiEvent {
    data class ShowSimilarPhotos(val photo: Photo) : FavoriteNavigationEvent()
}