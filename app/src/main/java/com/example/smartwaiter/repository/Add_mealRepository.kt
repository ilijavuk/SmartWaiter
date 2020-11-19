package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
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
}