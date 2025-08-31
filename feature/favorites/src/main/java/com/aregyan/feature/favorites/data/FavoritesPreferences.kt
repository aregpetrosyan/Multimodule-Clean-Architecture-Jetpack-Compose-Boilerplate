package com.aregyan.feature.favorites.data

import com.aregyan.core.datastore.DataStoreKeys
import com.aregyan.core.datastore.DataStoreManager
import com.aregyan.core.domain.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class FavoritesPreferences @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    private val key = DataStoreKeys.FAVORITES_PHOTOS
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun toggleFavorite(photo: Photo): Boolean {
        val currentJsonSet = dataStoreManager.read(key, emptySet()).first()
        val currentPhotos = currentJsonSet
            .mapNotNull { safeDecode(it) }
            .toMutableSet()

        val existingPhoto = currentPhotos.find { it.imageUrl == photo.imageUrl }
        val isFavorite: Boolean

        if (existingPhoto != null) {
            currentPhotos.remove(existingPhoto)
            isFavorite = false
        } else {
            currentPhotos.add(photo.copy(isFavorite = true))
            isFavorite = true
        }

        val updatedJsonSet = currentPhotos.map { json.encodeToString(it) }.toSet()
        dataStoreManager.write(key, updatedJsonSet)

        return isFavorite
    }

    fun observeFavorites(): Flow<Set<Photo>> =
        dataStoreManager.read(key, emptySet()).map { jsonSet ->
            jsonSet.mapNotNull { safeDecode(it) }.toSet()
        }

    private fun safeDecode(jsonStr: String): Photo? =
        runCatching { json.decodeFromString<Photo>(jsonStr) }.getOrNull()
}