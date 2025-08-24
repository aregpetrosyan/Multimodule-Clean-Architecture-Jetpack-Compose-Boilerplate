package com.aregyan.feature.favorites

import com.aregyan.core.domain.Photo
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteState(
    val photos: List<Photo>,
    val selectedPhoto: Photo? = null,
)