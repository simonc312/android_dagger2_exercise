package com.simonc312.androidapiexercise.api.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.simonc312.androidapiexercise.BuildConfig
import com.simonc312.androidapiexercise.api.models.Guide.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, indices = arrayOf(Index("name")))
class Guide(@PrimaryKey
            @SerializedName("name")
            var name: String = "",
            @SerializedName("url")
            var guideUrl: String = "",
            @SerializedName("icon")
            var iconUrl: String = "",
            @SerializedName("startDate")
            var startDate: String?,
            @SerializedName("endDate")
            var endDate: String?,
            @Embedded
            @SerializedName("venue")
            var venue: Venue?) {
    //entity requires getters for all fields not ignored so using var instead of val
    constructor() : this("","","",null,null, null)

    //Todo use Delegate class
    val venueDisplayValue: String
        get() {
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
    //Todo use Delegate class
    val dateRangeDisplayValue: String
        get() {
            if (startDate == null || endDate == null) {
                return "Dates unavailable"
            }
            return String.format("%s to %s", startDate, endDate)
        }

    //TODO useDelegate class
    fun getFullGuideUrl(): String {
        return BuildConfig.BASE_URL_ENDPOINT + guideUrl
    }

    companion object {
        const val TABLE_NAME = "guide"
    }
}
