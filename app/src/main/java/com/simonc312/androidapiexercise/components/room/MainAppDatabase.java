package com.simonc312.androidapiexercise.components.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.simonc312.androidapiexercise.api.models.Guide;

/**
 * Created by Simon on 5/17/2017.
 */

@Database(entities = {
        Guide.class
        },
        version = 1, // must be at least 1 else throws IllegalArgument RuntimeException
        exportSchema = false)
public abstract class MainAppDatabase extends RoomDatabase {
    public abstract GuideDAO guideDAO();
        }
