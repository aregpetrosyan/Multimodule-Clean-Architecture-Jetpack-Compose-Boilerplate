package com.aregyan.feature.explore

import androidx.lifecycle.viewModelScope
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.failure
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiState
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.UiEvent
import com.aregyan.core.ui.base.UiIntent
import com.aregyan.core.ui.base.setError
import com.aregyan.core.ui.base.setLoading
import com.aregyan.core.ui.base.setSuccess
import com.aregyan.feature.explore.domain.ExplorePhotosUseCase
import com.aregyan.feature.favorites.api.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val explorePhotosUseCase: ExplorePhotosUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val analyticsTracker: AnalyticsTracker
) : BaseViewModel<ExploreIntent, LceUiState<ExploreState>>() {

    override fun createInitialState() = LceUiState.Idle

    override fun handleIntent(intent: ExploreIntent) {
        when (intent) {
            ExploreIntent.LoadPhotos -> loadPhotos()
            is ExploreIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            is ExploreIntent.OnSimilarClick -> onSimilarClick(intent.photo)
            is ExploreIntent.OnPhotoClick -> onPhotoClick(intent.photo)
            else -> { /* No action required */
            }
        }
    }

    init {
        onIntent(ExploreIntent.LoadPhotos)
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            _state.setLoading()

            combine(
                explorePhotosUseCase(),
                favoritesUseCase.observeFavorites()
            ) { photoDomainList, favorites ->
                photoDomainList.map { photo ->
                    photo.map {
                        it.copy(isFavorite = favorites.any { favorite -> favorite.imageUrl == it.imageUrl })
                    }
                }
            }
                .onEach { result ->
                    result.onSuccess { photos ->
                        _state.setSuccess(ExploreState(photos))
                        analyticsTracker.imagesLoaded()
                    }.onFailure { throwable ->
                        _state.setError(throwable)
                        analyticsTracker.failure(throwable)
                    }
                }
                .collect()
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
            _navigationEvents.emit(ExploreNavigationEvent.ShowSimilarPhotos(photo))
        }
    }
}

sealed class ExploreIntent : UiIntent {
    object LoadPhotos : ExploreIntent()
    data class OnFavoriteClick(val photo: Photo) : ExploreIntent()
    data class OnSimilarClick(val photo: Photo) : ExploreIntent()
    data class OnPhotoClick(val photo: Photo?) : ExploreIntent()
    object Retry : ExploreIntent(), RetryIntentMarker
}

sealed class ExploreNavigationEvent : UiEvent {
    data class ShowSimilarPhotos(val photo: Photo) : ExploreNavigationEvent()
}