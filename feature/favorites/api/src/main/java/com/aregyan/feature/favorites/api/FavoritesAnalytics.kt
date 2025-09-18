package com.aregyan.feature.favorites.api

interface FavoritesAnalytics {

    fun logFavoriteSelection(itemId: String, isFavorite: Boolean)
}