package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Restoran
import hr.foi.air.webservice.model.Tag
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


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

    suspend fun getAllTags(
        table: String,
        method: String
    )= safeApiCall{
        RetrofitInstance.api.getAllTags(table, method)
    }

    suspend fun tagsByRestaurant(
        method : String,
        lokal_id : String
    )= safeApiCall{
        RetrofitInstance.api.tagsByRestaurant(method, lokal_id)
    }
    suspend fun menuByTag(
        method : String,
        id_tag : String,
        lokal_id : String
    )= safeApiCall{
        RetrofitInstance.api.menuByTag(method, id_tag, lokal_id)
    }


    suspend fun insertTag(
        table: String,
        method: String,
        tag: String

    ) = safeApiCall {
        RetrofitInstance.api.insertTag(table, method, tag)
    }
    suspend fun bindTag(
        table: String,
        method: String,
        stavka_id: String,
        tag_id: String

    ) = safeApiCall {
        RetrofitInstance.api.bindTag(table, method, stavka_id,tag_id)
    }
    suspend fun tagsByMeal(
        function : String,
        lokal_id : String,
    )  = safeApiCall {
        RetrofitInstance.api.tagsByMeal(function, lokal_id)
    }

    suspend fun RemoveTagsFromMeal(
        function : String,
        lokal_id : String,
    ) = safeApiCall {
        RetrofitInstance.api.RemoveTagsFromMeal(function, lokal_id)
    }



}