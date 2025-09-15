package com.aregyan.feature.explore

import androidx.lifecycle.viewModelScope
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.failure
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiState
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.SystemIntentMarker
import com.aregyan.core.ui.base.UiEvent
import com.aregyan.core.ui.base.UiIntent
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

    init {
        onIntent(ExploreIntent.LoadPhotos)
    }

    override fun handleIntent(intent: ExploreIntent) {
        _state.value = reduce(state.value, intent)

        when (intent) {
            ExploreIntent.LoadPhotos -> loadPhotos()
            is ExploreIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            is ExploreIntent.OnSimilarClick -> onSimilarClick(intent.photo)
            is ExploreIntent.PhotosLoaded -> analyticsTracker.imagesLoaded()
            is ExploreIntent.Error -> analyticsTracker.failure(intent.throwable)
            else -> {}
        }
    }

    override fun reduce(
        currentState: LceUiState<ExploreState>,
        intent: ExploreIntent
    ): LceUiState<ExploreState> = when (intent) {
        ExploreIntent.LoadPhotos ->
            LceUiState.loading()

        is ExploreIntent.PhotosLoaded ->
            if (intent.photos.isEmpty()) LceUiState.idle()
            else LceUiState.success(ExploreState(intent.photos))

        is ExploreIntent.Error ->
            LceUiState.error(intent.throwable)

        is ExploreIntent.OnPhotoClick -> {
            val state = currentState.data
            if (state != null) {
                LceUiState.success(state.copy(selectedPhoto = intent.photo))
            } else {
                currentState
            }
        }

        else -> currentState
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            combine(
                explorePhotosUseCase(),
                favoritesUseCase.observeFavorites()
            ) { photoDomainList, favorites ->
                photoDomainList.map { photo ->
                    photo.map {
                        it.copy(
                            isFavorite = favorites.any { fav -> fav.imageUrl == it.imageUrl }
                        )
                    }
                }
            }.onEach { result ->
                result.onSuccess { photos ->
                    onIntent(ExploreIntent.PhotosLoaded(photos))
                }.onFailure { throwable ->
                    onIntent(ExploreIntent.Error(throwable))
                }
            }.collect()
        }
    }

    private fun onFavoriteClick(photo: Photo) {
        viewModelScope.launch {
            favoritesUseCase.toggleFavorite(photo)
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
    data class PhotosLoaded(val photos: List<Photo>) : ExploreIntent()
    data class Error(val throwable: Throwable) : ExploreIntent(), SystemIntentMarker
    object Retry : ExploreIntent(), RetryIntentMarker
}

sealed class ExploreNavigationEvent : UiEvent {
    data class ShowSimilarPhotos(val photo: Photo) : ExploreNavigationEvent()
}