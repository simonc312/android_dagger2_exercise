package com.simonc312.androidapiexercise.dagger;

import com.simonc312.androidapiexercise.MainActivityInstrumentedTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        MainModule.class
})
public interface FakeMainAppComponent extends MainAppComponent {

    MainActivityInstrumentedTest injectsMainActivityInstrumentedTest(MainActivityInstrumentedTest testClass);
}
