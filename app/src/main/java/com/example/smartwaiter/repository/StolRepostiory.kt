package com.example.smartwaiter.repository

import com.example.database.UserPreferences
import hr.foi.air.webservice.RetrofitInstance

class StolRepostiory(private val preferences: UserPreferences) : BaseRepository(){

    suspend fun getTableByHash(
        table: String,
        method: String,
        hash: String
    )= safeApiCall {
        RetrofitInstance.api.getTableFromHash(table, method, hash)
    }

    suspend fun saveActiveRestaurant(token: String) {
        preferences.saveActiveRestaurant(token)
    }


}