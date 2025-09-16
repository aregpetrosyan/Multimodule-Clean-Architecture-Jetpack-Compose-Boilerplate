package com.aregyan.feature.random.di

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.feature.random.RandomAnalytics
import com.aregyan.feature.random.data.RandomApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RandomProvidesModule {
    @Provides
    @Singleton
    fun provideRandomApiService(retrofit: Retrofit): RandomApiService {
        return retrofit.create(RandomApiService::class.java)
    }

    @Provides
    fun provideRandomAnalytics(tracker: AnalyticsTracker): RandomAnalytics =
        RandomAnalytics(tracker)
}