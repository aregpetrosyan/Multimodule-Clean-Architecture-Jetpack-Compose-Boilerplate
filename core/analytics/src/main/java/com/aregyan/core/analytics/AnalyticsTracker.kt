package com.aregyan.core.analytics

interface AnalyticsTracker {
    fun log(event: AnalyticsEvent)
    fun setUserProperty(property: UserProperty)
}
