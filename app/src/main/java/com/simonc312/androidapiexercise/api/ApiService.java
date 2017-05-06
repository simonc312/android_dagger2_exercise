package com.simonc312.androidapiexercise.api;

import com.simonc312.androidapiexercise.api.models.Guides;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Simon on 5/4/2017.
 */

public interface ApiService {

    @GET("/service/v2/upcomingGuides/")
    Call<Guides> getUpcomingGuides();
}
