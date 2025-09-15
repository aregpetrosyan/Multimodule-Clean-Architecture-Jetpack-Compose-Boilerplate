package com.aregyan.feature.favorites

import androidx.lifecycle.viewModelScope
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModelOld
import com.aregyan.core.ui.base.LceUiStateOld
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.UiEvent
import com.aregyan.core.ui.base.UiIntent
import com.aregyan.core.ui.base.updateSuccess
import com.aregyan.feature.favorites.api.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCase: FavoritesUseCase
) : BaseViewModelOld<FavoritesIntent, LceUiStateOld<FavoriteState>>() {

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

    override fun reduce(
        currentState: LceUiStateOld<FavoriteState>,
        intent: FavoritesIntent
    ): LceUiStateOld<FavoriteState> = when (intent) {
        FavoritesIntent.LoadFavorites, FavoritesIntent.Retry -> LceUiStateOld.Loading()

        is FavoritesIntent.FavoritesLoaded ->
            if (intent.favorites.isEmpty()) LceUiStateOld.Idle
            else LceUiStateOld.Success(FavoriteState(intent.favorites.toList()))

        is FavoritesIntent.OnPhotoClick ->
            currentState.updateSuccess { data ->
                data.copy(selectedPhoto = intent.photo)
            }

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