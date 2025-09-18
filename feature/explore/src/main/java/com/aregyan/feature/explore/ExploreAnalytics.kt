package com.aregyan.feature.explore

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.BaseAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi

class ExploreAnalytics(
    tracker: AnalyticsTracker
) : BaseAnalytics(tracker), FavoritesAnalyticsApi