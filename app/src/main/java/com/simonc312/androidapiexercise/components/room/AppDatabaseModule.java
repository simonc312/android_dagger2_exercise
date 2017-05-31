package com.simonc312.androidapiexercise.components.room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Simon on 5/17/2017.
 */

@Module
public class AppDatabaseModule {

    @Provides
    @Singleton
    MainAppDatabase providesAppDatabase(@NonNull final Context context) {
        return Room.databaseBuilder(context, MainAppDatabase.class, "guide-database")
                .build();
    }

}
