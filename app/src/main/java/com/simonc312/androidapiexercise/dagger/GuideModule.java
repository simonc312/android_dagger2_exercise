package com.simonc312.androidapiexercise.dagger;

import android.support.annotation.NonNull;

import com.simonc312.androidapiexercise.GuideInteractor;
import com.simonc312.androidapiexercise.GuideRepository;
import com.simonc312.androidapiexercise.UpcomingGuidePresenter;
import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.components.room.MainAppDatabase;

import java.util.concurrent.ThreadPoolExecutor;

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

    @Provides
    GuideRepository provideGuideRespository(@NonNull final MainAppDatabase mainAppDatabase,
                                            @NonNull final ThreadPoolExecutor threadPoolExecutor) {
        return new GuideRepository(mainAppDatabase.guideDAO(), threadPoolExecutor);
    }

    //todo should persistent across configuration changes
    @Provides
    GuideInteractor provideGuideInteractor(@NonNull final ApiService apiService,
                                           @NonNull final GuideRepository guideRepository) {
        return new GuideInteractor(apiService, guideRepository);
    }
}
