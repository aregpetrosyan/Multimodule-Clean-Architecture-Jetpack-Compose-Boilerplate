package com.aregyan.feature.similar

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.BaseAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi

class SimilarAnalytics(
    tracker: AnalyticsTracker
) : BaseAnalytics(tracker), FavoritesAnalyticsApi