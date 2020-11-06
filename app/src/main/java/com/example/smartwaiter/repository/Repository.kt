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
}