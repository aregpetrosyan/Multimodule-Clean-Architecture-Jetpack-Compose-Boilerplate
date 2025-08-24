package com.aregyan.feature.random

import androidx.lifecycle.viewModelScope
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.failure
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiState
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.UiIntent
import com.aregyan.core.ui.base.setError
import com.aregyan.core.ui.base.setLoading
import com.aregyan.core.ui.base.setSuccess
import com.aregyan.feature.favorites.api.FavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomViewModel @Inject constructor(
    private val randomPhotoUseCase: RandomPhotoUseCase,
    private val favoritesUseCase: FavoritesUseCase,
    private val analyticsTracker: AnalyticsTracker
) : BaseViewModel<RandomIntent, LceUiState<Photo>>() {

    override fun createInitialState() = LceUiState.Idle

    override fun handleIntent(intent: RandomIntent) {
        when (intent) {
            RandomIntent.LoadRandomPhoto -> loadRandomPhoto()
            is RandomIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            else -> { /* No action required */
            }
        }
    }

    init {
        onIntent(RandomIntent.LoadRandomPhoto)
    }

    private fun loadRandomPhoto() {
        viewModelScope.launch {
            _state.setLoading()

            combine(
                randomPhotoUseCase(),
                favoritesUseCase.observeFavorites()
            ) { photoDomain, favorites ->
                photoDomain.map { photo ->
                    photo.copy(isFavorite = favorites.any { favorite -> favorite.imageUrl == photo.imageUrl })
                }
            }
                .onEach { result ->
                    result.onSuccess { photo ->
                        _state.setSuccess(photo)
                        analyticsTracker.photoLoaded(photo)
                    }.onFailure { throwable ->
                        _state.setError(throwable)
                        analyticsTracker.failure(throwable)
                    }
                }.collect()
        }
    }

    private fun onFavoriteClick(photo: Photo) {
        viewModelScope.launch {
            favoritesUseCase.toggleFavorite(photo)
        }
    }

}

sealed class RandomIntent : UiIntent {
    object LoadRandomPhoto : RandomIntent()
    data class OnFavoriteClick(val photo: Photo) : RandomIntent()
    object Retry : RandomIntent(), RetryIntentMarker
}