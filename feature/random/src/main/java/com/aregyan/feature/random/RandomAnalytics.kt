package com.aregyan.feature.random

import com.aregyan.core.analytics.AnalyticsConstants
import com.aregyan.core.analytics.AnalyticsEvent
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.withParam

class RandomAnalytics(
    val tracker: AnalyticsTracker
) {

    fun logLoadNewPhoto(imageId: String) {
        tracker.log(
            AnalyticsEvent(LOAD_NEW_PHOTO_CLICKED)
                .withParam(AnalyticsConstants.PARAM_IMAGE_ID, imageId)
        )
    }

    companion object {
        const val LOAD_NEW_PHOTO_CLICKED = "load_new_photo_clicked"
    }
}