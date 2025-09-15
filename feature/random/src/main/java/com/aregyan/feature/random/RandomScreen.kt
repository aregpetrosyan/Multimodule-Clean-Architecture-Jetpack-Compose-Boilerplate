package com.aregyan.feature.random

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.aregyan.core.ui.base.StateHandlerOld
import com.aregyan.feature_random.R

@Composable
fun RandomScreen(
    viewModel: RandomViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    StateHandlerOld(
        state = state,
        onRetry = { viewModel.onIntent(RandomIntent.Retry) }
    ) { photo ->
        Box {
            Image(
                painter = rememberAsyncImagePainter(photo.imageUrl),
                contentDescription = photo.description,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp),
                onClick = { viewModel.onIntent(RandomIntent.LoadRandomPhoto) },
            ) {
                Icon(painterResource(R.drawable.ic_refresh_24dp), "Refresh")
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(32.dp),
                onClick = { viewModel.onIntent(RandomIntent.OnFavoriteClick(photo)) },
            ) {
                Icon(
                    painterResource(if (photo.isFavorite) com.aregyan.core.ui.R.drawable.favorite_filled_24dp else com.aregyan.core.ui.R.drawable.favorite_24dp),
                    null
                )
            }
        }
    }
}