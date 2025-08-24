package com.aregyan.core.ui.components.photoGrid

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.R

enum class PhotoCardMode {
    FULL,          // show both buttons
    FAVORITE_ONLY, // show only favorite button
}

@Composable
fun PhotoCard(
    photo: Photo,
    photoCardMode: PhotoCardMode,
    onClick: (PhotoGridUiAction) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Square image
            Image(
                painter = rememberAsyncImagePainter(photo.imageUrl),
                contentDescription = photo.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clickable { onClick(PhotoGridUiAction.PhotoClick(photo)) }
            )

            // Bottom action row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Similar button
                if (photoCardMode == PhotoCardMode.FULL && photo.description != null) {
                    IconButton(
                        onClick = { onClick(PhotoGridUiAction.SimilarClick(photo)) }
                    ) {
                        Icon(
                            painterResource(R.drawable.similar_24dp),
                            contentDescription = "Similar"
                        )
                    }
                }

                // Favorite button
                IconButton(
                    onClick = { onClick(PhotoGridUiAction.FavoriteClick(photo)) }
                ) {
                    Icon(
                        painterResource(
                            if (photo.isFavorite) R.drawable.favorite_filled_24dp
                            else R.drawable.favorite_24dp
                        ),
                        contentDescription = "Favorite"
                    )
                }
            }
        }
    }
}
