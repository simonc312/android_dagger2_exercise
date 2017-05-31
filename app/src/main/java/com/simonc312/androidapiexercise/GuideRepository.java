package com.simonc312.androidapiexercise;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.simonc312.androidapiexercise.api.models.Guide;
import com.simonc312.androidapiexercise.components.room.GuideDAO;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Simon on 5/29/2017.
 */

public class GuideRepository {
    private final GuideDAO guideDAO;
    private final Executor backgroundJobExecutor;

    public GuideRepository(@NonNull final GuideDAO guideDAO,
                           @NonNull final  Executor backgroundJobExecutor) {
        this.guideDAO = guideDAO;
        this.backgroundJobExecutor = backgroundJobExecutor;
    }

    public void add(List<Guide> guides) {
        final AsyncTask<List<Guide>, Void, Void> insertGuidesTask = new AsyncTask<List<Guide>, Void, Void>() {
            @Override
            protected Void doInBackground(List<Guide> ... guides) {
                GuideRepository.this.guideDAO.insertGuides(guides[0]);
                return null;
            }
        };
        insertGuidesTask.executeOnExecutor(backgroundJobExecutor, guides);
    }

    public void get(@Nullable final String query,
                    @NonNull final Callback callback) {
        final AsyncTask<Void, Void,List<Guide>> fetchGuidesTask = new AsyncTask<Void, Void, List<Guide>>() {
            @Override
            protected List<Guide> doInBackground(Void... voids) {
                if (query == null || query.isEmpty()) {
                    return GuideRepository.this.guideDAO.getAllGuides();
                } else {
                    // Todo vulnerable to sql injection from query
                    return GuideRepository.this.guideDAO.getGuidesWithName(GuideDAO.WILDCARD +query+ GuideDAO.WILDCARD);
                }
            }

            @Override
            protected void onPostExecute(List<Guide> guides) {
                callback.onRepositoryResponse(guides);
            }
        };
        fetchGuidesTask.executeOnExecutor(backgroundJobExecutor);
    }

    interface Callback {
        void onRepositoryResponse(List<Guide> guides);
    }
}
