package com.aregyan.feature.favorites

import com.aregyan.core.datastore.DataStoreManager
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
}