package com.simonc312.androidapiexercise.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.simonc312.androidapiexercise.BuildConfig;

public class Guide {
    @SerializedName("name")
    private final String name;

    @SerializedName("url")
    private final String guideUrl;

    @SerializedName("icon")
    private final String iconUrl;

    @SerializedName("startDate")
    private final String startDate;

    @SerializedName("endDate")
    private final String endDate;

    @SerializedName("venue")
    private final Venue venue;

    public Guide(String name, String guideUrl, String iconUrl, String startDate, String endDate, Venue venue) {
        this.name = name;
        this.guideUrl = guideUrl;
        this.iconUrl = iconUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
    }

    //Todo move to ViewModel class
    @NonNull
    public String getVenueDisplayValue() {
        String city = venue.getCity();
        String state = venue.getState();
        if (city == null || state == null) {
            return "Venue Location TBA";
        }
        return String.format("%s, %s", city, state);
    }
    //Todo move to ViewModel class
    @NonNull
    public String getDateRangeDisplayValue() {
        if (startDate == null || endDate == null) {
            return "Dates unavailable";
        }
        return String.format("%s to %s", startDate, endDate);
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }
    //TODO modify with gson converter
    public String getGuideUrl() {
        return BuildConfig.BASE_URL_ENDPOINT + guideUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
