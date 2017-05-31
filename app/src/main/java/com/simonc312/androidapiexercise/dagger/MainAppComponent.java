package com.simonc312.androidapiexercise.dagger;

import com.simonc312.androidapiexercise.components.room.AppDatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        MainModule.class,
        AppDatabaseModule.class,
})
public interface MainAppComponent {

    GuideSubcomponent plus(GuideModule guideModule);
}
