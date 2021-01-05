package com.example.database.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.database.db.models.OrderedMeal

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(orderedMeal: OrderedMeal)

    @Query("SELECT * FROM orderedmeals")
    fun getOrderedMeals() : LiveData<List<OrderedMeal>>

    @Delete
    suspend fun deleteMeal(orderedMeal: OrderedMeal)

    @Query("DELETE FROM orderedmeals")
    suspend fun deleteAllFromOrder()
}