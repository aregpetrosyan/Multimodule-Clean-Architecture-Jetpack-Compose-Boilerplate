package com.aregyan.core.data.apiMappers

import com.aregyan.core.data.api.ExploreResponse
import com.aregyan.core.domain.Photo

fun List<ExploreResponse>.toDomain(): List<Photo> {
    return this.map { photo ->
        Photo(
            imageUrl = photo.urls.regular,
            description = photo.description ?: photo.altDescription,
        )
    }
}