package com.aregyan.feature.favorites.di

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.datastore.DataStoreManager
import com.aregyan.feature.favorites.FavoritesAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi
import com.aregyan.feature.favorites.data.FavoritesPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavoritesProvidesModule {
    @Provides
    @Singleton
    fun provideFavoritesPreferences(dataStoreManager: DataStoreManager): FavoritesPreferences {
        return FavoritesPreferences(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideFavoritesAnalytics(
        tracker: AnalyticsTracker,
        favoritesAnalyticsApi: FavoritesAnalyticsApi
    ): FavoritesAnalytics {
        return FavoritesAnalytics(tracker, favoritesAnalyticsApi)
    }
}