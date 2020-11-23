package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Restoran
import retrofit2.Response


class Add_mealRepository {
    suspend fun insertMeal(
        table: String,
        method: String,
        mealName: String,
        mealPrice: String,
        mealDescription: String,
        mealPhotoPath: String,
        lokalId: String
    ): Response<String> {
        return RetrofitInstance.api.insertMeal(table, method, mealName, mealPrice, mealDescription, mealPhotoPath, lokalId)
    }

    suspend fun getMeal(
        table: String,
        method: String,
        lokal_id: String
    ): Response<List<Meal>> {
        return RetrofitInstance.api.getMeal(table, method, lokal_id)
    }

    suspend fun getMealById(
        table: String,
        method: String,
        id_stavka: String
    ): Response<List<Meal>> {
        return RetrofitInstance.api.getMealById(table, method, id_stavka)
    }

    suspend fun setMealAvailability(
        table: String,
        method: String,
        mealId: String,
        available: String,
    ){
        return RetrofitInstance.api.setMealAvailability(table, method, mealId, available)
    }

}