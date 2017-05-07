package com.simonc312.androidapiexercise.dagger;

import android.support.annotation.NonNull;

import com.simonc312.androidapiexercise.UpcomingGuidePresenter;
import com.simonc312.androidapiexercise.api.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class GuideModule {

    private final UpcomingGuidePresenter.View view;

    public GuideModule(@NonNull final UpcomingGuidePresenter.View view) {
        this.view = view;
    }

    @Provides
    UpcomingGuidePresenter provideUpcomingGuidePresenter(@NonNull final ApiService apiService) {
        return new UpcomingGuidePresenter(apiService, this.view);
    }
}
