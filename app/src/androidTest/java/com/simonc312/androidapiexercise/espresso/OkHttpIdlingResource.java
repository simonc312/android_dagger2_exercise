package com.simonc312.androidapiexercise.espresso;

import android.support.test.espresso.IdlingResource;

import okhttp3.Dispatcher;

/**
 * Ripped from https://github.com/JakeWharton/okhttp-idling-resource
 * Created by Simon on 5/13/2017.
 */
public class OkHttpIdlingResource implements IdlingResource {

    private final Dispatcher dispatcher;
    private volatile ResourceCallback callback;

    public OkHttpIdlingResource(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.dispatcher.setIdleCallback(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onTransitionToIdle();
                }
            }
        });
    }

    @Override
    public String getName() {
        return OkHttpIdlingResource.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        return dispatcher.runningCallsCount() == 0;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }
}
