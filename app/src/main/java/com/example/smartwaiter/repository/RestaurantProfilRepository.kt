package com.example.smartwaiter.repository

import com.example.database.UserPreferences

class RestaurantProfilRepository(
    private val preferences: UserPreferences
) {

     suspend fun logout() {
        preferences.clear()
    }
}