package com.simonc312.androidapiexercise.dagger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.simonc312.androidapiexercise.components.room.MainAppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by simonchen on 6/1/17.
 */

@Module
public class FakeAppDatabaseModule {

    @Provides
    @Singleton
    MainAppDatabase providesFakeAppDatabase(@NonNull final Context context) {
        return Room.inMemoryDatabaseBuilder(context, MainAppDatabase.class).build();
    }
}
