package com.aregyan.core.ui.components.photoGrid

import com.aregyan.core.domain.Photo

sealed class PhotoGridUiAction {
    data class FavoriteClick(val photo: Photo) : PhotoGridUiAction()
    data class SimilarClick(val photo: Photo) : PhotoGridUiAction()
    data class PhotoClick(val photo: Photo?) : PhotoGridUiAction()
}