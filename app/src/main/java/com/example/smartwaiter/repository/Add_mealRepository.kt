package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Restoran
import retrofit2.Response


class Add_mealRepository : BaseRepository() {
    suspend fun insertMeal(
        table: String,
        method: String,
        mealName: String,
        mealPrice: String,
        mealDescription: String,
        mealPhotoPath: String,
        lokalId: String
    ) = safeApiCall {
         RetrofitInstance.api.insertMeal(table, method, mealName, mealPrice, mealDescription, mealPhotoPath, lokalId)
    }

    suspend fun updateMeal(
        table: String,
        method: String,
        mealId: String,
        mealName: String,
        mealPrice: String,
        mealDescription: String,
        mealPhotoPath: String,
    ) = safeApiCall{
        RetrofitInstance.api.updateMeal(table, method, mealId, mealName, mealPrice, mealDescription, mealPhotoPath)
    }

    suspend fun getMeal(
        table: String,
        method: String,
        lokal_id: String
    )= safeApiCall {
        RetrofitInstance.api.getMeal(table, method, lokal_id)
    }

    suspend fun getMealById(
        table: String,
        method: String,
        id_stavka: String
    )= safeApiCall {
        RetrofitInstance.api.getMealById(table, method, id_stavka)
    }

    suspend fun setMealAvailability(
        table: String,
        method: String,
        mealId: String,
        available: String,
    )= safeApiCall{
        RetrofitInstance.api.setMealAvailability(table, method, mealId, available)
    }

}