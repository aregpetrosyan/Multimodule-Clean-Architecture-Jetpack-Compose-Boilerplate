package com.aregyan.feature.similar

import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.BaseAnalytics
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi
import javax.inject.Inject

class SimilarAnalytics @Inject constructor(
    tracker: AnalyticsTracker,
    private val favoritesAnalyticsApi: FavoritesAnalyticsApi
) : BaseAnalytics(tracker), FavoritesAnalyticsApi by favoritesAnalyticsApi