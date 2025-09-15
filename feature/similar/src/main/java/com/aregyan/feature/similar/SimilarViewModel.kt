package com.aregyan.feature.similar

import androidx.lifecycle.viewModelScope
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.BaseViewModelOld
import com.aregyan.core.ui.base.LceUiStateOld
import com.aregyan.core.ui.base.RetryIntentMarker
import com.aregyan.core.ui.base.SystemIntentMarker
import com.aregyan.core.ui.base.UiIntent
import com.aregyan.core.ui.base.updateSuccess
import com.aregyan.feature.favorites.api.FavoritesUseCase
import com.aregyan.feature.similar.domain.SimilarPhotosUseCase
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
) : BaseViewModelOld<SimilarIntent, LceUiStateOld<SimilarState>>() {

    override fun handleIntent(intent: SimilarIntent) {
        _state.value = reduce(state.value, intent)

        when (intent) {
            is SimilarIntent.LoadSimilarPhotos -> loadSimilarPhotos(intent.query)
            is SimilarIntent.OnFavoriteClick -> onFavoriteClick(intent.photo)
            else -> {}
        }
    }

    override fun reduce(
        currentState: LceUiStateOld<SimilarState>,
        intent: SimilarIntent
    ): LceUiStateOld<SimilarState> = when (intent) {
        is SimilarIntent.LoadSimilarPhotos -> LceUiStateOld.Loading()

        is SimilarIntent.SimilarPhotosLoaded ->
            LceUiStateOld.Success(SimilarState(intent.photos))

        is SimilarIntent.OnPhotoClick -> currentState.updateSuccess { data ->
            data.copy(selectedPhoto = intent.photo)
        }

        is SimilarIntent.Error -> LceUiStateOld.Error(intent.throwable)

        else -> currentState
    }

    private fun loadSimilarPhotos(query: String) {
        viewModelScope.launch {
            combine(
                similarPhotosUseCase.invoke(query),
                favoritesUseCase.observeFavorites()
            ) { photosDomain, favorites ->
                photosDomain.map { photo ->
                    photo.map {
                        it.copy(isFavorite = favorites.any { favorite -> favorite.imageUrl == it.imageUrl })
                    }
                }
            }
                .onEach { result ->
                    result.onSuccess { photos ->
                        onIntent(SimilarIntent.SimilarPhotosLoaded(photos))
                    }.onFailure { throwable ->
                        onIntent(SimilarIntent.Error(throwable))
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
}

sealed class SimilarIntent : UiIntent {
    data class LoadSimilarPhotos(val query: String) : SimilarIntent()
    data class OnFavoriteClick(val photo: Photo) : SimilarIntent()
    data class OnPhotoClick(val photo: Photo?) : SimilarIntent()
    data class SimilarPhotosLoaded(val photos: List<Photo>) : SimilarIntent()
    data class Error(val throwable: Throwable) : SimilarIntent(), SystemIntentMarker
    object Retry : SimilarIntent(), RetryIntentMarker
}