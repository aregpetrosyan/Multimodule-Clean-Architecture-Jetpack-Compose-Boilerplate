package com.aregyan.feature.explore.di

import com.aregyan.feature.explore.data.ExploreApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExploreProvidesModule {
    @Provides
    @Singleton
    fun provideExploreApiService(retrofit: Retrofit): ExploreApiService {
        return retrofit.create(ExploreApiService::class.java)
    }
}