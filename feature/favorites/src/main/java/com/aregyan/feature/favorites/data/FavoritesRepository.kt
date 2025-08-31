package com.aregyan.feature.favorites.data

import com.aregyan.core.domain.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoritesPreferences: FavoritesPreferences
) {

    suspend fun toggleFavorite(photo: Photo): Result<Boolean> {
        val result = favoritesPreferences.toggleFavorite(photo)
        return Result.success(result)
    }

    fun observeFavorites(): Flow<Set<Photo>> {
        return favoritesPreferences.observeFavorites()
    }
}
