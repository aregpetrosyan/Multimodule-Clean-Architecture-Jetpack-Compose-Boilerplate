package com.aregyan.core.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideRandomApiService(retrofit: Retrofit): RandomApiService {
        return retrofit.create(RandomApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSimilarApiService(retrofit: Retrofit): SimilarApiService {
        return retrofit.create(SimilarApiService::class.java)
    }
}