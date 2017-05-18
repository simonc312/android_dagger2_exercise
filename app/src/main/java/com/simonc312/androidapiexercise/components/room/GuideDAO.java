package com.simonc312.androidapiexercise.components.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.simonc312.androidapiexercise.api.models.Guide;

import java.util.List;

/**
 * Created by Simon on 5/17/2017.
 */

@Dao
public interface GuideDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertGuides(List<Guide> guides);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateGuides(List<Guide> guides);

    @Delete
    void deleteGuides(List<Guide> guides);

    @Query("SELECT * FROM " + Guide.TABLE_NAME)
    List<Guide> getAllGuides();

    @Query("SELECT * FROM " + Guide.TABLE_NAME + " WHERE name LIKE :search")
    List<Guide> getGuidesWithName(String search);
}
