package com.example.database.db

import androidx.lifecycle.LiveData
import androidx.room.*
import hr.foi.air.webservice.model.Meal

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Query("SELECT * FROM meals")
    fun getMeals() : LiveData<List<Meal>>

    @Delete
    suspend fun deleteMeal(meal: Meal)
}