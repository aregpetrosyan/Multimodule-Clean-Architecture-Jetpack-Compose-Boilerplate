package com.aregyan.feature.favorites.api

interface FavoritesAnalyticsApi {

    fun logFavoriteSelection(itemId: String, isFavorite: Boolean)
}