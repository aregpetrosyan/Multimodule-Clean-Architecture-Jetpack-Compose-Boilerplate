package com.aregyan.core.ui.components.photoGrid

import androidx.compose.runtime.Composable
import com.aregyan.core.domain.Photo
import com.aregyan.core.ui.components.FullscreenImageViewer

private const val IMAGE_NOT_FOUND = -1

@Composable
fun PhotoGridWithImageViewer(
    photos: List<Photo>,
    selectedPhoto: Photo?,
    photoCardMode: PhotoCardMode = PhotoCardMode.FULL,
    onClick: (PhotoGridUiAction) -> Unit
) {
    PhotoGrid(
        photos = photos,
        photoCardMode = photoCardMode,
        onClick = onClick
    )

    val selectedPhotoIndex = photos.indexOf(selectedPhoto)
    if (selectedPhotoIndex != IMAGE_NOT_FOUND) {
        FullscreenImageViewer(
            photos = photos,
            startIndex = selectedPhotoIndex,
            onClose = { onClick(PhotoGridUiAction.PhotoClick(null)) }
        )
    }
}