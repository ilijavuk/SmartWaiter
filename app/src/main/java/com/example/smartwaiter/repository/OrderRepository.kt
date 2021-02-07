package com.example.smartwaiter.repository

import com.android.volley.Response
import com.example.database.UserPreferences
import com.example.database.db.SMDatabase
import com.example.database.db.models.OrderedMeal
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Order



class OrderRepository(val db: SMDatabase, private val preferences: UserPreferences) : BaseRepository() {

    suspend fun upsert(orderedMeal: OrderedMeal) = db.getDAO().upsert(orderedMeal)

    fun getMeals() = db.getDAO().getOrderedMeals()

    suspend fun deleteMeal(orderedMeal: OrderedMeal) = db.getDAO().deleteMeal(orderedMeal)

    suspend fun deleteAllFromOrder() = db.getDAO().deleteAllFromOrder()

    suspend fun makeOrder(
        table: String,
        method: String,
        user_id: Int,
        table_id: Int,
        status: Int,
        time: String,
        meal_id: Int,
        amount: Int
    ) = safeApiCall {
        RetrofitInstance.api.makeOrder(
            table,
            method,
            user_id,
            table_id,
            status,
            time,
            meal_id,
            amount
        )
    }

    suspend fun saveOrderBucket(show: Boolean) {
        preferences.saveOrderBucket(show)
    }

    suspend fun pushNotification(
        lokal_id: Int,
    ) {
        RetrofitInstance.api.pushNotification("1",lokal_id)
    }

}


