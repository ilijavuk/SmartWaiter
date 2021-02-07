package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.RestaurantRating
import hr.foi.air.webservice.model.Restoran
import retrofit2.Response

class RateRestaurantRepository {
    suspend fun pushRating(
        table: String,
        method: String,
        user_id: String,
        lokal_id: String,
        rating: String
    ) {
        return RetrofitInstance.api.pushRating(table,method,user_id,lokal_id,rating)
    }

    suspend fun getRating(
        table: String,
        method: String,
        user_id: String,
        lokal_id: String,
    ): Response<List<RestaurantRating>>
    {
        return RetrofitInstance.api.getRating(table,method,user_id,lokal_id)
    }

    suspend fun getRestaurantName(
        lokal_id: String
    ): Response<List<Restoran>> {
        return RetrofitInstance.api.getRestaurantName("Lokal","select",lokal_id)
    }
}