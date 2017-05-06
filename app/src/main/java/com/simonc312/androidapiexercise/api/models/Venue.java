package com.simonc312.androidapiexercise.api.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Venue {
    @SerializedName("city")
    private final String city;

    @SerializedName("state")
    private final String state;

    public Venue(String city, String state) {
        this.city = city;
        this.state = state;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    @Nullable
    public String getState() {
        return state;
    }
}
