package com.aregyan.feature.favorites.di

import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi
import com.aregyan.feature.favorites.api.FavoritesUseCase
import com.aregyan.feature.favorites.domain.FavoritesAnalyticsApiImpl
import com.aregyan.feature.favorites.domain.FavoritesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FavoritesBindsModule {

    @Binds
    @Singleton
    abstract fun bindFavoritesUseCase(
        impl: FavoritesUseCaseImpl
    ): FavoritesUseCase

    @Binds
    @Singleton
    abstract fun bindFavoritesAnalytics(
        impl: FavoritesAnalyticsApiImpl
    ): FavoritesAnalyticsApi
}
