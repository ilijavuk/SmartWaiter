package com.example.smartwaiter.repository

import com.example.database.db.SMDatabase
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal

class OrderRepository(val db: SMDatabase) : BaseRepository() {

    suspend fun makeOrder(
        table: String,
        method: String,
        user_id: String,
        restaurantTable_id: String,
        meal_id:String,
        amount: String,
        time: String,
    ) = safeApiCall { RetrofitInstance.api.makeOrder(table, method, user_id, restaurantTable_id, meal_id ,amount, time) }

    suspend fun upsert(meal: Meal) = db.getDAO().upsert(meal)

    fun getMeals() = db.getDAO().getMeals()

    suspend fun deleteMeal(meal: Meal) = db.getDAO().deleteMeal(meal)

}