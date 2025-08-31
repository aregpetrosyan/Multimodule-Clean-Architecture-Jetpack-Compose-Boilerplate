package com.aregyan.feature.favorites.api

import com.aregyan.core.domain.Photo
import kotlinx.coroutines.flow.Flow

interface FavoritesUseCase {
    suspend fun toggleFavorite(photo: Photo): Result<Boolean>
    fun observeFavorites(): Flow<Set<Photo>>
}