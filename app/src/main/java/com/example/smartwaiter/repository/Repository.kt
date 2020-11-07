package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Korisnik
import retrofit2.Response

class Repository {

    suspend fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ): Response<List<Korisnik>>{
        return RetrofitInstance.api.getKorisnik(table, method, username, password)
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

}