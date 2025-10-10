package com.aregyan.core.analytics

import android.content.Context
import android.util.Base64
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

    /**
     * NOTE: The Mixpanel token below is hardcoded and only encoded with Base64 (not real encryption).
     * This is insecure and should ONLY be used for local testing or boilerplate/sample projects.
     *
     * Do NOT include this in production builds. For production:
     *  - Use remote config, secure backend, or encrypted storage.
     *  - Never hardcode analytics tokens or secrets.
     */
    @Provides
    @Singleton
    fun provideMixpanel(@ApplicationContext context: Context): MixpanelAPI {
        // Base64-encoded Mixpanel project token for testing / boilerplate only.
        val encodedToken = "ZjMyYzlkODY0MWQ0YTQyOTc4ZGFmYzQ1Y2E0MTE4YTU="

        val token = String(Base64.decode(encodedToken, Base64.DEFAULT))
        return MixpanelAPI.getInstance(context, token, true)
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
