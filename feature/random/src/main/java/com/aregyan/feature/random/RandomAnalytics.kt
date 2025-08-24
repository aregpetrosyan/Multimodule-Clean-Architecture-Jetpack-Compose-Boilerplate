package com.aregyan.feature.random

import com.aregyan.core.analytics.AnalyticsEvent
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.UserProperty
import com.aregyan.core.domain.Photo

object RandomAnalytics {
    const val IMAGE_LOADED = "image_loaded"
    const val IMAGE_URL = "image_url"
}

fun AnalyticsTracker.photoLoaded(photo: Photo) {
    log(AnalyticsEvent(RandomAnalytics.IMAGE_LOADED))
    setUserProperty(UserProperty(RandomAnalytics.IMAGE_URL, photo.imageUrl))
}