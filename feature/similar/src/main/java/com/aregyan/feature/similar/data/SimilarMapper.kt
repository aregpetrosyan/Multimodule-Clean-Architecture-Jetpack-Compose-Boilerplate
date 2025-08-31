package com.aregyan.feature.similar.data

import com.aregyan.core.domain.Photo

fun SimilarResponse.toDomain(): List<Photo> {
    return this.results.map { photo ->
        Photo(
            imageUrl = photo.urls.regular,
            description = photo.description
        )
    }
}