package com.aregyan.feature.favorites.di

import com.aregyan.feature.favorites.FavoritesUseCaseImpl
import com.aregyan.feature.favorites.api.FavoritesUseCase
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
}
