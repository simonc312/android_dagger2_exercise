package com.simonc312.androidapiexercise.dagger;

import com.simonc312.androidapiexercise.MainActivity;

import dagger.Subcomponent;

@Subcomponent(modules = {
        GuideModule.class
})
public interface GuideSubcomponent {

    MainActivity injectsMainActivity(MainActivity mainActivity);
}
