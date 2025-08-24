package com.aregyan.core.data.apiMappers

import com.aregyan.core.data.api.RandomResponse
import com.aregyan.core.domain.Photo

fun RandomResponse.toDomain(): Photo {
    return Photo(
        imageUrl = this.urls.regular,
        description = this.description
    )
}