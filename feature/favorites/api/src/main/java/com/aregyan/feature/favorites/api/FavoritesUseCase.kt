package com.aregyan.feature.favorites.api

import com.aregyan.core.domain.Photo
import jakarta.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {

    suspend fun toggleFavorite(photo: Photo): Result<Boolean> {
        return favoritesRepository.toggleFavorite(photo)
    }

    fun observeFavorites() = favoritesRepository.observeFavorites()
}