package com.aregyan.feature.random

import com.aregyan.core.analytics.AnalyticsConstants
import com.aregyan.core.analytics.AnalyticsEvent
import com.aregyan.core.analytics.AnalyticsTracker

class RandomAnalytics(
    val tracker: AnalyticsTracker
) {

    fun logLoadNewPhoto(imageId: String) {
        tracker.log(
            AnalyticsEvent(
                name = LOAD_NEW_PHOTO_CLICKED,
                params = mapOf(AnalyticsConstants.PARAM_IMAGE_ID to imageId)
            )
        )
    }

    companion object {
        const val LOAD_NEW_PHOTO_CLICKED = "load_new_photo_clicked"
    }
}