package com.aregyan.feature.random.data

import com.aregyan.core.domain.Photo

fun RandomResponse.toDomain(): Photo {
    return Photo(
        imageUrl = this.urls.regular,
        description = this.description
    )
}