package com.example.smartwaiter.repository

import com.example.database.db.SMDatabase
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal

class OrderRepository(val db: SMDatabase) : BaseRepository() {


    suspend fun upsert(meal: Meal) = db.getDAO().upsert(meal)

    fun getMeals() = db.getDAO().getMeals()

    suspend fun deleteMeal(meal: Meal) = db.getDAO().deleteMeal(meal)

}