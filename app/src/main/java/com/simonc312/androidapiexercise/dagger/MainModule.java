package com.simonc312.androidapiexercise.dagger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import com.simonc312.androidapiexercise.R;
import com.simonc312.androidapiexercise.executors.BackgroundJobExecutor;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ThreadPoolExecutor;

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

    @Provides
    @Singleton
    CustomTabsIntent.Builder provideCustomTabsIntentBuilder(@NonNull final Context context) {
        return new CustomTabsIntent.Builder() //TODO pass session
                .enableUrlBarHiding()
                .addDefaultShareMenuItem()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    @Provides
    @Singleton
    ThreadPoolExecutor provideThreadPoolExecutor() {
        return BackgroundJobExecutor.getInstance();
    }
}
