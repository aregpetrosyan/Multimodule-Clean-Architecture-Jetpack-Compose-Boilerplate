package com.aregyan.core.analytics

class BaseAnalytics(
    val tracker: AnalyticsTracker
) {
    fun logError(throwable: Throwable, eventName: String = AnalyticsConstants.EVENT_GENERIC_ERROR) {
        tracker.log(
            AnalyticsEvent(
                name = eventName,
                params = mapOf(
                    AnalyticsConstants.PARAM_MESSAGE to (throwable.message ?: "Unknown"),
                    AnalyticsConstants.PARAM_TYPE to throwable::class.java.simpleName
                )
            )
        )
    }

    fun logFavoriteSelection(itemId: String, isFavorite: Boolean) {
        tracker.log(
            AnalyticsEvent(
                name = AnalyticsConstants.EVENT_FAVORITE_SELECTION,
                params = mapOf(
                    AnalyticsConstants.PARAM_ITEM_ID to itemId,
                    AnalyticsConstants.PARAM_IS_FAVORITE to isFavorite
                )
            )
        )
    }

    fun logOpenImage(imageId: String) {
        tracker.log(
            AnalyticsEvent(
                name = AnalyticsConstants.EVENT_OPEN_IMAGE,
                params = mapOf(AnalyticsConstants.PARAM_IMAGE_ID to imageId)
            )
        )
    }

    fun logOpenSimilarImages(imageId: String) {
        tracker.log(
            AnalyticsEvent(
                name = AnalyticsConstants.EVENT_OPEN_SIMILAR_IMAGES,
                params = mapOf(AnalyticsConstants.PARAM_IMAGE_ID to imageId)
            )
        )
    }
}