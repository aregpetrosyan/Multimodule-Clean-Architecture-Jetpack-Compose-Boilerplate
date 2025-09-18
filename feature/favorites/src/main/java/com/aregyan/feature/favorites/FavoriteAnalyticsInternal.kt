package com.aregyan.feature.favorites

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.BaseAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi

class FavoriteAnalyticsInternal(
    tracker: AnalyticsTracker
) : BaseAnalytics(tracker), FavoritesAnalyticsApi