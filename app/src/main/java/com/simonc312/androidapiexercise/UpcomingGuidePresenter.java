package com.simonc312.androidapiexercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.api.models.Guide;
import com.simonc312.androidapiexercise.api.models.Guides;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presents {@link Guide}
 * data in displayable form through view contract.
 */

public class UpcomingGuidePresenter implements Callback<Guides> {

    private final ApiService apiService;
    private final View view;
    @Nullable
    private Call<Guides> currentCall;

    public UpcomingGuidePresenter(@NonNull final ApiService apiService,
                                  @NonNull final View view) {
        this.apiService = apiService;
        this.view = view;
    }

    /**
     * Make request to get upcoming guide date
     */
    public void get() {
        this.currentCall = this.apiService.getUpcomingGuides();
        this.currentCall.enqueue(this);
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
        this.view.update(response.body().getData());
    }

    @Override
    public void onFailure(@NonNull final Call<Guides> call,
                          @NonNull final Throwable t) {
        this.view.onError(t);
    }
    //endregion

    public interface View {
        void update(@NonNull List<Guide> newGuides);

        void onError(@NonNull Throwable error);
    }
}
