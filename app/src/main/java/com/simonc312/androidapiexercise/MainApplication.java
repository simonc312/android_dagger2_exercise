package com.simonc312.androidapiexercise;

import android.app.Application;
import android.support.annotation.NonNull;

import com.simonc312.androidapiexercise.dagger.DaggerMainAppComponent;
import com.simonc312.androidapiexercise.dagger.GuideModule;
import com.simonc312.androidapiexercise.dagger.GuideSubcomponent;
import com.simonc312.androidapiexercise.dagger.MainAppComponent;
import com.simonc312.androidapiexercise.dagger.MainModule;

public class MainApplication extends Application {

    private static MainApplication instance;

    private MainAppComponent mainAppComponent;

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MainApplication.instance = this;
        this.mainAppComponent = DaggerMainAppComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }

    public MainAppComponent getMainAppComponent() {
        return mainAppComponent;
    }


    public static GuideSubcomponent getGuideComponent(@NonNull final UpcomingGuidePresenter.View view) {
        return instance.mainAppComponent.plus(new GuideModule(view));
    }
}
