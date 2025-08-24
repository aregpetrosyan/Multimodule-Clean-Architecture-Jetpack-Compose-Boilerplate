package com.aregyan.core.analytics

import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject

class MixpanelAnalyticsTracker(
    private val mixpanel: MixpanelAPI
) : AnalyticsTracker {

    override fun log(event: AnalyticsEvent) {
        val props = JSONObject()
        event.params.forEach { (key, value) ->
            props.put(key, value)
        }
        mixpanel.track(event.name, props)
    }

    override fun setUserProperty(property: UserProperty) {
        val peopleProps = JSONObject().apply {
            put(property.key, property.value)
        }
        mixpanel.people.set(peopleProps)
    }
}