package com.example.smartwaiter.repository

import com.example.database.UserPreferences
import hr.foi.air.webservice.RetrofitInstance


class AuthRepository(private val preferences: UserPreferences) : BaseRepository() {

    suspend fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ) = safeApiCall { RetrofitInstance.api.getKorisnik(table, method, username, password) }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

    suspend fun saveUserType(userType: String) {
        preferences.saveUserType(userType)
    }

    suspend fun saveCustomerID(customerID: String){
        preferences.saveCustomerID(customerID)
    }

    suspend fun createCustomer() = safeApiCall { RetrofitInstance.api.createCustomer() }

}