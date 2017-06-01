package com.simonc312.androidapiexercise.api.models

import com.simonc312.androidapiexercise.BuildConfig

/**
 * Contains extension functions for {@link Guide}
 * Can only be used by kotlin files
 * while values generated from kotlin class are accessible in java files
 * Created by simonchen on 5/31/17.
 */

//required to be top level to be visible and accessible
fun Guide.getDateDisplay(): String {
    if (startDate == null || endDate == null) {
        return "Dates unavailable"
    }
    return String.format("%s to %s", startDate, endDate)
}

fun Guide.getVenueDisplay(): String {
    if (venue == null) {
        return "Venue Location TBA"
    }
    val city = venue?.city
    val state = venue?.state
    if (city == null || state == null) {
        return "Venue Location TBA"
    }
    return String.format("%s, %s", city, state)
}

fun Guide.getFullGuideUrl(): String {
    return BuildConfig.BASE_URL_ENDPOINT + guideUrl
}


