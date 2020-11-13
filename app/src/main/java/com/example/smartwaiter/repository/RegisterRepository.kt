package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Korisnik
import hr.foi.air.webservice.model.Restoran
import retrofit2.Response

class RegisterRepository {

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
    ): Response<List<Korisnik>> {
        return RetrofitInstance.api.getUsername(table, method, username)
    }
}