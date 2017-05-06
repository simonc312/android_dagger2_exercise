package com.simonc312.androidapiexercise.dagger;

import com.simonc312.androidapiexercise.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApiModule.class,
        MainModule.class
})
public interface MainAppComponent {

    MainActivity injectsMainActivity(MainActivity mainActivity);
}
