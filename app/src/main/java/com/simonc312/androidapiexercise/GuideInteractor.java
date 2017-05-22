package com.simonc312.androidapiexercise;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.api.models.Guide;
import com.simonc312.androidapiexercise.api.models.Guides;
import com.simonc312.androidapiexercise.components.room.GuideDAO;

import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Simon on 5/17/2017.
 */

public class GuideInteractor implements Callback<Guides> {
    private final ApiService apiService;
    private final GuideDAO guideDAO;
    private Call<Guides> currentCall;
    private InteractorOutput interactorOutput;
    private Executor backgroundJobExecutor;

    public GuideInteractor(@NonNull final ApiService apiService,
                           @NonNull final GuideDAO guideDAO,
                           @NonNull final Executor backgroundJobExecutor) {
        this.apiService = apiService;
        this.guideDAO = guideDAO;
        this.backgroundJobExecutor = backgroundJobExecutor;
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
        this.interactorOutput.onGuidesAvailable(guides);
        final AsyncTask<List<Guide>, Void, Void> insertGuidesTask = new AsyncTask<List<Guide>, Void, Void>() {
            @Override
            protected Void doInBackground(List<Guide> ... guides) {
                GuideInteractor.this.guideDAO.insertGuides(guides[0]);
                return null;
            }
        };
        insertGuidesTask.executeOnExecutor(backgroundJobExecutor, guides);
    }

    @Override
    public void onFailure(@NonNull final Call<Guides> call,
                          @NonNull final Throwable t) {
        if (t instanceof UnknownHostException) {
            getFromLocalStore(null /*query defaults to all*/);
        }
        this.interactorOutput.onGuidesUnavailable();
    }

    private void getFromLocalStore(@Nullable final String query) {
        final AsyncTask<Void, Void,List<Guide>> fetchGuidesTask = new AsyncTask<Void, Void, List<Guide>>() {
            @Override
            protected List<Guide> doInBackground(Void... voids) {
                if (query == null || query.isEmpty()) {
                    return GuideInteractor.this.guideDAO.getAllGuides();
                } else {
                    // Todo vulnerable to sql injection from query
                    return GuideInteractor.this.guideDAO.getGuidesWithName(GuideDAO.WILDCARD+query+GuideDAO.WILDCARD);
                }
            }

            @Override
            protected void onPostExecute(List<Guide> guides) {
                GuideInteractor.this.interactorOutput.onGuidesAvailableOffline(guides);
            }
        };
        fetchGuidesTask.executeOnExecutor(backgroundJobExecutor);
    }

    public void setOutput(@NonNull final InteractorOutput output) {
        this.interactorOutput = output;
    }

    //endregion

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
