package com.aregyan.feature.favorites

import com.aregyan.core.domain.Photo
import com.aregyan.feature.favorites.api.FavoritesUseCase
import com.aregyan.feature.favorites.data.FavoritesRepository
import jakarta.inject.Inject

class FavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : FavoritesUseCase {

    override suspend fun toggleFavorite(photo: Photo): Result<Boolean> {
        return favoritesRepository.toggleFavorite(photo)
    }

    override fun observeFavorites() = favoritesRepository.observeFavorites()
}