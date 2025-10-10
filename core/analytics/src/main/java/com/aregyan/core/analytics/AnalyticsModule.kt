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
     * NOTE: The Mixpanel token below is hardcoded and only Base64-encoded for simplicity.
     * It is included here solely for demonstration and convenience in this boilerplate project.
     *
     * In a real application, avoid hardcoding tokens. Instead, consider:
     *  - Fetching the token securely from a backend service,
     *  - Storing it in encrypted storage or the Android Keystore,
     *  - Or providing it via build-time configuration (e.g., BuildConfig or environment variables).
     */
    @Provides
    @Singleton
    fun provideMixpanel(@ApplicationContext context: Context): MixpanelAPI {
        // Base64-encoded Mixpanel project token used for simplicity in this boilerplate project.
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
