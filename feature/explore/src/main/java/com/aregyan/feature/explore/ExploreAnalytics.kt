package com.aregyan.feature.explore

import com.aregyan.core.analytics.AnalyticsEvent
import com.aregyan.core.analytics.AnalyticsTracker

object ExploreAnalytics {
    const val IMAGES_LOADED = "images_loaded"
}

fun AnalyticsTracker.imagesLoaded() {
    log(AnalyticsEvent(ExploreAnalytics.IMAGES_LOADED))
}