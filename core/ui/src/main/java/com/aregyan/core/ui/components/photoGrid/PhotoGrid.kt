package com.aregyan.core.ui.components.photoGrid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aregyan.core.domain.Photo

@Composable
fun PhotoGrid(
    photos: List<Photo>,
    photoCardMode: PhotoCardMode,
    onClick: (PhotoGridUiAction) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(photos) { photo ->
            PhotoCard(
                photo = photo,
                photoCardMode = photoCardMode,
                onClick = onClick
            )
        }
    }
}