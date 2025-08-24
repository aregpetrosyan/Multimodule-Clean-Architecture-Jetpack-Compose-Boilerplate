package com.aregyan.core.data.apiMappers

import com.aregyan.core.data.api.SimilarResponse
import com.aregyan.core.domain.Photo

fun SimilarResponse.toDomain(): List<Photo> {
    return this.results.map { photo ->
        Photo(
            imageUrl = photo.urls.regular,
            description = photo.description
        )
    }
}