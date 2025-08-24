package com.aregyan.core.analytics

object AnalyticsThrowable {
    const val NETWORK_FAILURE = "network_failure"
    const val THROWABLE_MESSAGE = "throwable_message"
}

fun AnalyticsTracker.failure(throwable: Throwable) {
    log(AnalyticsEvent(AnalyticsThrowable.NETWORK_FAILURE).apply {
        params.apply {
            Pair(AnalyticsThrowable.THROWABLE_MESSAGE, throwable.message)
        }
    })
}