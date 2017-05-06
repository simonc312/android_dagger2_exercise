package com.simonc312.androidapiexercise.api.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Guides {
    @SerializedName("total")
    private final int total;

    @SerializedName("data")
    private final List<Guide> data;


    public Guides(final int total,
                  @NonNull final List<Guide> data) {
        this.total = total;
        this.data = data;
    }

    public List<Guide> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}
