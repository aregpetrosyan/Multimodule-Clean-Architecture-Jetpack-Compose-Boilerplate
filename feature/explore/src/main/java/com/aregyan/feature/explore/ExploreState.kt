package com.aregyan.feature.explore

import com.aregyan.core.domain.Photo
import kotlinx.serialization.Serializable

@Serializable
data class ExploreState(
    val photos: List<Photo>,
    val selectedPhoto: Photo? = null,
)