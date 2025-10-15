package com.aregyan.feature.random

import androidx.lifecycle.viewModelScope
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiState
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.SystemIntentMarker
import com.aregyan.core.ui.base.UiIntent
import com.aregyan.feature.favorites.api.FavoritesUseCase
import com.aregyan.feature.random.domain.RandomPhotoUseCase
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
    private val randomAnalytics: RandomAnalytics
) : BaseViewModel<RandomIntent, Photo>() {

    init {
        onIntent(RandomIntent.InitialLoadPhoto)
    }

    override fun handleIntent(intent: RandomIntent) {
        _state.value = reduce(state.value, intent)

        when (intent) {
            RandomIntent.LoadNewPhoto, RandomIntent.InitialLoadPhoto -> loadRandomPhoto(intent)
            is RandomIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            is RandomIntent.Error -> randomAnalytics.logError(intent.throwable)
            else -> {}
        }
    }

    override fun reduce(
        currentState: LceUiState<Photo>,
        intent: RandomIntent
    ): LceUiState<Photo> = when (intent) {
        RandomIntent.LoadNewPhoto, RandomIntent.InitialLoadPhoto ->
            LceUiState.loading()

        is RandomIntent.PhotoLoaded ->
            LceUiState.success(intent.photo)

        is RandomIntent.Error ->
            LceUiState.error(intent.throwable)

        else -> currentState
    }

    private fun loadRandomPhoto(intent: RandomIntent) {
        viewModelScope.launch {
            combine(
                randomPhotoUseCase(),
                favoritesUseCase.observeFavorites()
            ) { photoDomain, favorites ->
                photoDomain.map { photo ->
                    photo.copy(isFavorite = favorites.any { fav -> fav.imageUrl == photo.imageUrl })
                }
            }.onEach { result ->
                result.onSuccess { photo ->
                    onIntent(RandomIntent.PhotoLoaded(photo))
                    if (intent is RandomIntent.LoadNewPhoto) {
                        randomAnalytics.logLoadNewPhoto(photo.imageUrl)
                    }
                }.onFailure { throwable ->
                    onIntent(RandomIntent.Error(throwable))
                }
            }.collect()
        }
    }

    private fun onFavoriteClick(photo: Photo) {
        viewModelScope.launch {
            favoritesUseCase.toggleFavorite(photo)
        }
        randomAnalytics.logFavoriteSelection(photo.imageUrl, !photo.isFavorite)
    }
}

sealed class RandomIntent : UiIntent {
    object InitialLoadPhoto : RandomIntent()
    object LoadNewPhoto : RandomIntent()
    data class OnFavoriteClick(val photo: Photo) : RandomIntent()
    data class PhotoLoaded(val photo: Photo) : RandomIntent()
    data class Error(val throwable: Throwable) : RandomIntent(), SystemIntentMarker
    object Retry : RandomIntent(), RetryIntentMarker
}