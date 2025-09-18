package com.aregyan.feature.favorites

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.BaseAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalytics

class FavoriteAnalyticsInternal(
    tracker: AnalyticsTracker
) : BaseAnalytics(tracker), FavoritesAnalytics