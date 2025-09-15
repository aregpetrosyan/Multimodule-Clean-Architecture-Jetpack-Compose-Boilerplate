package com.aregyan.feature.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.base.StateHandler
import com.aregyan.core.ui.components.photoGrid.PhotoGridUiAction
import com.aregyan.core.ui.components.photoGrid.PhotoGridWithImageViewer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun FavoritesScreen(
    onSimilarClick: (Photo) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is FavoriteNavigationEvent.ShowSimilarPhotos -> onSimilarClick(event.photo)
            }
        }
    }

    StateHandler(
        state = state,
        onRetry = { viewModel.onIntent(FavoritesIntent.Retry) },
        idleContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.ic_rocket_48dp),
                    null
                )
            }
        }
    ) { favoriteState ->
        PhotoGridWithImageViewer(
            photos = favoriteState.photos,
            selectedPhoto = favoriteState.selectedPhoto,
            onClick = {
                when (it) {
                    is PhotoGridUiAction.FavoriteClick -> viewModel.onIntent(FavoritesIntent.OnFavoriteClick(it.photo))
                    is PhotoGridUiAction.PhotoClick -> viewModel.onIntent(FavoritesIntent.OnPhotoClick(it.photo))
                    is PhotoGridUiAction.SimilarClick -> viewModel.onIntent(FavoritesIntent.OnSimilarClick(it.photo))
                }
            }
        )
    }
}