package com.simonc312.androidapiexercise.api.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
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

    companion object {
        const val TABLE_NAME = "guide"
    }
}
