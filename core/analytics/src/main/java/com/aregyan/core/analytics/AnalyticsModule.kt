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
     * NOTE: The Mixpanel token is provided via BuildConfig.
     *
     * In a real application, consider more robust security practices, such as:
     *  - Fetching the token securely from a backend service.
     *  - Storing it in encrypted storage or the Android Keystore.
     */
    @Provides
    @Singleton
    fun provideMixpanel(@ApplicationContext context: Context): MixpanelAPI {
        val token = String(Base64.decode(BuildConfig.MIXPANEL_API_TOKEN, Base64.DEFAULT))
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
