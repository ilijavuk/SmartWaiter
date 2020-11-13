package com.example.smartwaiter.repository

import com.example.database.UserPreferences
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Korisnik
import retrofit2.Response

class AuthRepository(
    private val preferences: UserPreferences
) {

    suspend fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ): Response<List<Korisnik>>{
        return RetrofitInstance.api.getKorisnik(table, method, username, password)
    }

    suspend fun saveAuthToken(token: String){
        preferences.saveAuthToken(token)
    }

    suspend fun saveUserType(userType: String){
        preferences.saveUserType(userType)
    }

}


/*

*/