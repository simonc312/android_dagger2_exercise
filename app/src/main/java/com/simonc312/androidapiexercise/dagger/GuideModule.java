package com.simonc312.androidapiexercise.dagger;

import android.support.annotation.NonNull;

import com.simonc312.androidapiexercise.GuideInteractor;
import com.simonc312.androidapiexercise.UpcomingGuidePresenter;
import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.components.room.MainAppDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class GuideModule {

    private final UpcomingGuidePresenter.View view;

    public GuideModule(@NonNull final UpcomingGuidePresenter.View view) {
        this.view = view;
    }

    @Provides
    UpcomingGuidePresenter provideUpcomingGuidePresenter(@NonNull final GuideInteractor guideInteractor) {
        final UpcomingGuidePresenter presenter = new UpcomingGuidePresenter(guideInteractor, this.view);
        guideInteractor.setOutput(presenter);
        return presenter;
    }

    //todo should persistent across configuration changes
    @Provides
    GuideInteractor provideGuideInteractor(@NonNull final ApiService apiService,
                                           @NonNull final MainAppDatabase mainAppDatabase) {
        return new GuideInteractor(apiService, mainAppDatabase.guideDAO());
    }
}
