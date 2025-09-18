package com.aregyan.feature.favorites

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.BaseAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi

class FavoritesAnalytics(
    tracker: AnalyticsTracker,
    private val favoritesAnalyticsApi: FavoritesAnalyticsApi
) : BaseAnalytics(tracker), FavoritesAnalyticsApi by favoritesAnalyticsApi