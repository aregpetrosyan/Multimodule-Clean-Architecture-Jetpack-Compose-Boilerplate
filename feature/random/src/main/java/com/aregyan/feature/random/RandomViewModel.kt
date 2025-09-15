package com.aregyan.feature.random

import androidx.lifecycle.viewModelScope
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.failure
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModel
import com.aregyan.core.ui.base.LceUiStateOld
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
    private val analyticsTracker: AnalyticsTracker
) : BaseViewModel<RandomIntent, LceUiStateOld<Photo>>() {

    init {
        onIntent(RandomIntent.LoadRandomPhoto)
    }

    override fun handleIntent(intent: RandomIntent) {
        _state.value = reduce(state.value, intent)

        when (intent) {
            RandomIntent.LoadRandomPhoto -> loadRandomPhoto()
            is RandomIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            is RandomIntent.PhotoLoaded -> analyticsTracker.photoLoaded(intent.photo)
            is RandomIntent.Error -> analyticsTracker.failure(intent.throwable)
            else -> {}
        }
    }

    override fun reduce(
        currentState: LceUiStateOld<Photo>,
        intent: RandomIntent
    ): LceUiStateOld<Photo> = when (intent) {
        RandomIntent.LoadRandomPhoto ->
            LceUiStateOld.Loading()

        is RandomIntent.PhotoLoaded ->
            LceUiStateOld.Success(intent.photo)

        is RandomIntent.Error ->
            LceUiStateOld.Error(throwable = intent.throwable)

        else -> currentState
    }

    private fun loadRandomPhoto() {
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
    }
}

sealed class RandomIntent : UiIntent {
    object LoadRandomPhoto : RandomIntent()
    data class OnFavoriteClick(val photo: Photo) : RandomIntent()
    data class PhotoLoaded(val photo: Photo) : RandomIntent()
    data class Error(val throwable: Throwable) : RandomIntent(), SystemIntentMarker
    object Retry : RandomIntent(), RetryIntentMarker
}