package com.aregyan.feature.favorites

import com.aregyan.core.datastore.FavoritesPreferences
import com.aregyan.core.domain.Photo
import com.aregyan.feature.favorites.api.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesPreferences: FavoritesPreferences
) : FavoritesRepository {

    override suspend fun toggleFavorite(photo: Photo): Result<Boolean> {
        val result = favoritesPreferences.toggleFavorite(photo)
        return Result.success(result)
    }

    override fun observeFavorites(): Flow<Set<Photo>> {
        return favoritesPreferences.observeFavorites()
    }
}
