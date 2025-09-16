package com.aregyan.core.analytics

open class BaseAnalytics(
    protected val tracker: AnalyticsTracker
) {
    fun logError(throwable: Throwable, eventName: String = EVENT_GENERIC_ERROR) {
        tracker.log(
            AnalyticsEvent(
                name = eventName,
                params = mapOf(
                    PARAM_MESSAGE to (throwable.message ?: "Unknown"),
                    PARAM_TYPE to throwable::class.java.simpleName
                )
            )
        )
    }

    fun logFavoriteSelection(itemId: String, isFavorite: Boolean) {
        tracker.log(
            AnalyticsEvent(
                name = EVENT_FAVORITE_SELECTION,
                params = mapOf(
                    PARAM_ITEM_ID to itemId,
                    PARAM_IS_FAVORITE to isFavorite
                )
            )
        )
    }

    fun logOpenImage(imageId: String) {
        tracker.log(
            AnalyticsEvent(
                name = EVENT_OPEN_IMAGE,
                params = mapOf(PARAM_IMAGE_ID to imageId)
            )
        )
    }

    companion object {
        const val EVENT_GENERIC_ERROR = "generic_error"
        const val EVENT_FAVORITE_SELECTION = "favorite_selection"
        const val EVENT_OPEN_IMAGE = "open_image"

        const val PARAM_MESSAGE = "message"
        const val PARAM_TYPE = "type"
        const val PARAM_ITEM_ID = "item_id"
        const val PARAM_IMAGE_ID = "image_id"
        const val PARAM_IS_FAVORITE = "is_favorite"
    }
}