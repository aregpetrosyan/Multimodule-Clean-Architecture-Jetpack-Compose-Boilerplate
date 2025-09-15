package com.aregyan.feature.similar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.StateHandler
import com.aregyan.core.ui.components.photoGrid.PhotoCardMode
import com.aregyan.core.ui.components.photoGrid.PhotoGridUiAction
import com.aregyan.core.ui.components.photoGrid.PhotoGridWithImageViewer

@Composable
fun SimilarScreen(
    photo: Photo,
    viewModel: SimilarViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(SimilarIntent.LoadSimilarPhotos(photo.description.orEmpty()))
    }

    StateHandler(
        state = state,
        onRetry = { viewModel.onIntent(SimilarIntent.Retry) }
    ) { similarState ->
        PhotoGridWithImageViewer(
            photos = similarState.photos,
            selectedPhoto = similarState.selectedPhoto,
            photoCardMode = PhotoCardMode.FAVORITE_ONLY,
            onClick = {
                when (it) {
                    is PhotoGridUiAction.FavoriteClick -> viewModel.onIntent(SimilarIntent.OnFavoriteClick(it.photo))
                    is PhotoGridUiAction.PhotoClick -> viewModel.onIntent(SimilarIntent.OnPhotoClick(it.photo))
                    else -> { /* No action required */
                    }
                }
            }
        )
    }
}