package com.aregyan.core.analytics

data class AnalyticsEvent(
    val name: String,
    val params: Map<String, Any> = emptyMap()
)

fun AnalyticsEvent.withParam(key: String, value: Any): AnalyticsEvent =
    this.copy(params = this.params + (key to value))