package com.aregyan.feature.similar

import androidx.lifecycle.viewModelScope
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
class SimilarViewModel @Inject constructor(
    private val similarPhotosUseCase: SimilarPhotosUseCase,
    private val favoritesUseCase: FavoritesUseCase,
) : BaseViewModel<SimilarIntent, LceUiState<SimilarState>>() {
    override fun createInitialState() = LceUiState.Idle

    override fun handleIntent(intent: SimilarIntent) {
        when (intent) {
            is SimilarIntent.LoadSimilarPhotos -> loadSimilarPhotos(intent.query)
            is SimilarIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            is SimilarIntent.OnPhotoClick -> onPhotoClick(intent.photo)
            else -> { /* No action required */
            }
        }
    }

    private fun loadSimilarPhotos(query: String) {
        _state.setLoading()
        viewModelScope.launch {
            combine(
                similarPhotosUseCase.invoke(query),
                favoritesUseCase.observeFavorites()
            ) { photoDomain, favorites ->
                photoDomain.map { photo ->
                    photo.map {
                        it.copy(isFavorite = favorites.any { favorite -> favorite.imageUrl == it.imageUrl })
                    }
                }
            }
                .onEach { result ->
                    result.onSuccess { photos ->
                        _state.setSuccess(SimilarState(photos))
                    }.onFailure { throwable ->
                        _state.setError(throwable)
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
}

sealed class SimilarIntent : UiIntent {
    data class LoadSimilarPhotos(val query: String) : SimilarIntent()
    data class OnFavoriteClick(val photo: Photo) : SimilarIntent()
    data class OnPhotoClick(val photo: Photo?) : SimilarIntent()
    object Retry : SimilarIntent(), RetryIntentMarker
}