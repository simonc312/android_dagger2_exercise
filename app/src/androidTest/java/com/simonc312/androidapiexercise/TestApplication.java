package com.simonc312.androidapiexercise;

import android.support.test.InstrumentationRegistry;

import com.simonc312.androidapiexercise.dagger.DaggerFakeMainAppComponent;
import com.simonc312.androidapiexercise.dagger.FakeMainAppComponent;
import com.simonc312.androidapiexercise.dagger.MainAppComponent;
import com.simonc312.androidapiexercise.dagger.MainModule;

public class TestApplication extends MainApplication {

    public FakeMainAppComponent getFakeMainAppComponent() {
        return (FakeMainAppComponent) getMainAppComponent();
    }

    public static TestApplication getInstance() {
        return (TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected MainAppComponent buildGraph() {
        return DaggerFakeMainAppComponent.builder()
                .mainModule(new MainModule(this))
                .build();
    }
}
