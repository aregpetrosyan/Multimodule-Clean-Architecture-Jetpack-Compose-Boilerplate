package com.aregyan.core.domain

import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    val imageUrl: String,
    val description: String?,
    val isFavorite: Boolean = false
)