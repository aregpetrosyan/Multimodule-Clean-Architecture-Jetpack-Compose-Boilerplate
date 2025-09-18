package com.aregyan.feature.similar.di

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.feature.similar.SimilarAnalytics
import com.aregyan.feature.similar.data.SimilarApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SimilarProviderModule {

    @Provides
    @Singleton
    fun provideSimilarApiService(retrofit: Retrofit): SimilarApiService {
        return retrofit.create(SimilarApiService::class.java)
    }

    @Provides
    fun provideSimilarAnalytics(tracker: AnalyticsTracker): SimilarAnalytics =
        SimilarAnalytics(tracker)
}