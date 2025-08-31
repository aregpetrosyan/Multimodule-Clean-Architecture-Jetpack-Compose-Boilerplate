package com.aregyan.feature.explore

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