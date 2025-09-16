package com.aregyan.core.analytics

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

    @Provides
    @Singleton
    fun provideMixpanel(@ApplicationContext context: Context): MixpanelAPI {
        return MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_TOKEN, true)
    }

    @Provides
    @Singleton
    fun provideAnalyticsTracker(mixpanel: MixpanelAPI): AnalyticsTracker {
        return MixpanelAnalyticsTracker(mixpanel)
    }

    @Provides
    @Singleton
    fun provideBaseAnalytics(tracker: AnalyticsTracker): BaseAnalytics {
        return BaseAnalytics(tracker)
    }
}
