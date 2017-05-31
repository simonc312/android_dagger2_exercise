package com.simonc312.androidapiexercise.components.room

import android.arch.persistence.room.*
import com.simonc312.androidapiexercise.api.models.Guide

/**
 * Created by Simon on 5/17/2017.
 */

@Dao
interface GuideDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuides(guides: List<Guide>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateGuides(guides: List<Guide>)

    @Delete
    fun deleteGuides(guides: List<Guide>)

    @get:Query("SELECT * FROM ${Guide.TABLE_NAME}")
    val allGuides: List<Guide>

    //kotlin renames all method parameters as arg0, arg1, etc
    @Query("SELECT * FROM ${Guide.TABLE_NAME} WHERE name LIKE :arg0")
    fun getGuidesWithName(search: String): List<Guide>

    companion object {

        const val WILDCARD = "%"

        const val SINGLE_WILDCARD = "_"
    }
}
