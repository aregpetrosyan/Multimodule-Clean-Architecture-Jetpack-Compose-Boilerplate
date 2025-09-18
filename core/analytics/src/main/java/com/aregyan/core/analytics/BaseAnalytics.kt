package com.aregyan.core.analytics

open class BaseAnalytics(
    val tracker: AnalyticsTracker
) {
    fun logError(throwable: Throwable, eventName: String = AnalyticsConstants.EVENT_GENERIC_ERROR) {
        tracker.log(
            AnalyticsEvent(name = eventName)
                .withParam(AnalyticsConstants.PARAM_MESSAGE, throwable.message ?: AnalyticsConstants.UNKNOWN)
                .withParam(AnalyticsConstants.PARAM_TYPE, throwable::class.java.simpleName)
        )
    }

    fun logFavoriteSelection(itemId: String, isFavorite: Boolean) {
        tracker.log(
            AnalyticsEvent(name = AnalyticsConstants.EVENT_FAVORITE_SELECTION)
                .withParam(AnalyticsConstants.PARAM_ITEM_ID, itemId)
                .withParam(AnalyticsConstants.PARAM_IS_FAVORITE, isFavorite)
        )
    }

    fun logOpenImage(imageId: String) {
        tracker.log(
            AnalyticsEvent(name = AnalyticsConstants.EVENT_OPEN_IMAGE)
                .withParam(AnalyticsConstants.PARAM_IMAGE_ID, imageId)
        )
    }

    fun logOpenSimilarImages(imageId: String) {
        tracker.log(
            AnalyticsEvent(name = AnalyticsConstants.EVENT_OPEN_SIMILAR_IMAGES)
                .withParam(AnalyticsConstants.PARAM_IMAGE_ID, imageId)
        )
    }
}