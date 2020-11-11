package com.example.smartwaiter.repository

import com.example.database.UserPreferences
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Korisnik
import hr.foi.air.webservice.model.Restoran
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
    suspend fun getRestorani(
        table: String,
        method: String,
    ): Response<List<Restoran>>{
        return RetrofitInstance.api.getRestorani(table, method)
    }

    suspend fun insertRestoran(
        table: String,
        method: String,
        restaurantName: String,
        restaurantAddress: String,
        GPS_Longitude: Double,
        GPS_Latitude: Double
    ): Response<String>{
        return RetrofitInstance.api.insertRestoran(table, method, restaurantName, restaurantAddress, GPS_Longitude, GPS_Latitude)
    }

    suspend fun RegisterKorisnik(
        table: String,
        method: String,
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        userType: String,
        password: String
    ){
        return RetrofitInstance.api.RegisterKorisnik(table, method, username, firstName, lastName, email, userType, password)
    }

    suspend fun getUsername(
        table: String,
        method: String,
        username: String,
    ): Response<List<Korisnik>>{
        return RetrofitInstance.api.getUsername(table, method, username)
    }
