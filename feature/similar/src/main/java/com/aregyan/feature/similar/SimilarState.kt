package com.aregyan.feature.similar

import com.aregyan.core.domain.Photo
import kotlinx.serialization.Serializable

@Serializable
data class SimilarState(
    val photos: List<Photo>,
    val selectedPhoto: Photo? = null,
)