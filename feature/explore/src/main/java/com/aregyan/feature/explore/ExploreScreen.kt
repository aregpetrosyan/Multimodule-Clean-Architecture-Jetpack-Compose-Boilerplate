package com.aregyan.feature.explore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.StateHandler
import com.aregyan.core.ui.components.photoGrid.PhotoGridUiAction
import com.aregyan.core.ui.components.photoGrid.PhotoGridWithImageViewer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExploreScreen(
    onSimilarClick: (Photo) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is ExploreNavigationEvent.ShowSimilarPhotos -> onSimilarClick(event.photo)
            }
        }
    }

    StateHandler(
        state = state,
        onRetry = { viewModel.onIntent(ExploreIntent.Retry) }
    ) { exploreState ->
        PhotoGridWithImageViewer(
            photos = exploreState.photos,
            selectedPhoto = exploreState.selectedPhoto,
            onClick = {
                val intent = when (it) {
                    is PhotoGridUiAction.FavoriteClick -> ExploreIntent.OnFavoriteClick(it.photo)
                    is PhotoGridUiAction.SimilarClick -> ExploreIntent.OnSimilarClick(it.photo)
                    is PhotoGridUiAction.PhotoClick -> ExploreIntent.OnPhotoClick(it.photo)
                }
                viewModel.onIntent(intent)
            }
        )
    }
}

