package com.aregyan.feature.favorites.domain

import com.aregyan.core.analytics.AnalyticsConstants
import com.aregyan.core.analytics.AnalyticsEvent
import com.aregyan.core.analytics.AnalyticsTracker
import com.aregyan.core.analytics.withParam
import com.aregyan.feature.favorites.api.FavoritesAnalyticsApi
import jakarta.inject.Inject

class FavoritesAnalyticsApiImpl @Inject constructor(
    private val tracker: AnalyticsTracker
) : FavoritesAnalyticsApi {
    override fun logFavoriteSelection(itemId: String, isFavorite: Boolean) {
        tracker.log(
            AnalyticsEvent(name = AnalyticsConstants.EVENT_FAVORITE_SELECTION)
                .withParam(AnalyticsConstants.PARAM_ITEM_ID, itemId)
                .withParam(AnalyticsConstants.PARAM_IS_FAVORITE, isFavorite)
        )
    }
}