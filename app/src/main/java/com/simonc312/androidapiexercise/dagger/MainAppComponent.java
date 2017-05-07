package com.simonc312.androidapiexercise.dagger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        MainModule.class
})
public interface MainAppComponent {

    GuideSubcomponent plus(GuideModule guideModule);
}
