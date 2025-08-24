package com.aregyan.feature.favorites.api

import com.aregyan.core.domain.Photo
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun toggleFavorite(photo: Photo): Result<Boolean>
    fun observeFavorites(): Flow<Set<Photo>>
}