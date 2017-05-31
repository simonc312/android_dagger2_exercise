package com.simonc312.androidapiexercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.api.models.Guide;
import com.simonc312.androidapiexercise.api.models.Guides;

import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Simon on 5/17/2017.
 */

public class GuideInteractor implements Callback<Guides>,GuideRepository.Callback {
    private final ApiService apiService;
    private final GuideRepository guideRepository;
    private Call<Guides> currentCall;
    private InteractorOutput interactorOutput;

    public GuideInteractor(@NonNull final ApiService apiService,
                           @NonNull final GuideRepository guideRepository) {
        this.apiService = apiService;
        this.guideRepository = guideRepository;
        this.interactorOutput = new EmptyInteractorOutput();
    }
    /**
     * Make request to get upcoming guide date
     */
    public void get() {
        this.currentCall = this.apiService.getUpcomingGuides();
        this.currentCall.enqueue(this);
    }

    public void get(String query) {
        getFromLocalStore(query);
    }

    /**
     * Cancel existing request.
     */
    public void cancel() {
        if (this.currentCall != null) {
            this.currentCall.cancel();
        }
    }

    //region Callback
    @Override
    public void onResponse(@NonNull final Call<Guides> call,
                           @NonNull final Response<Guides> response) {
        final List<Guide> guides = response.body().getData();
        handleRetrofitResponse(guides);
    }

    @VisibleForTesting
    void handleRetrofitResponse(List<Guide> guides) {
        this.interactorOutput.onGuidesAvailable(guides);
        this.guideRepository.add(guides);
    }

    @Override
    public void onFailure(@NonNull final Call<Guides> call,
                          @NonNull final Throwable t) {
       handleRetrofitFailure(t);
    }

    @VisibleForTesting
    void handleRetrofitFailure(@NonNull final Throwable t) {
        if (t instanceof UnknownHostException) {
            getFromLocalStore(null /*query defaults to all*/);
        }
        this.interactorOutput.onGuidesUnavailable();
    }

    @Override
    public void onRepositoryResponse(List<Guide> guides) {
        this.interactorOutput.onGuidesAvailableOffline(guides);
    }
    //endregion

    private void getFromLocalStore(@Nullable final String query) {
        this.guideRepository.get(query, this);
    }

    public void setOutput(@Nullable final InteractorOutput output) {
        this.interactorOutput = output;
    }

    public interface InteractorOutput {
        void onGuidesAvailable(List<Guide> guides);

        void onGuidesAvailableOffline(List<Guide> guides);

        void onGuidesUnavailable();
    }

    private static class EmptyInteractorOutput implements InteractorOutput {

        @Override
        public void onGuidesAvailable(List<Guide> guides) {
            //do nothing
        }

        @Override
        public void onGuidesAvailableOffline(List<Guide> guides) {

        }

        @Override
        public void onGuidesUnavailable() {

        }
    }
}
