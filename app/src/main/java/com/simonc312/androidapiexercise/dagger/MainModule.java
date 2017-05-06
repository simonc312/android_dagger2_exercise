package com.simonc312.androidapiexercise.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {


    private final Context applicationContext;

    public MainModule(@NonNull final Context context) {
        this.applicationContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.applicationContext;
    }

    @Provides
    @Singleton
    Picasso providePicasso(@NonNull final Context context) {
        return Picasso.with(context);
    }
}
