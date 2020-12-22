package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance

class OrderRepository : BaseRepository() {

    suspend fun makeOrder(
        table: String,
        method: String,
        user_id: String,
        restaurantTable_id: String,
        meal_id:String,
        amount: String,
        time: String,
    ) = safeApiCall { RetrofitInstance.api.makeOrder(table, method, user_id, restaurantTable_id, meal_id ,amount, time) }

}