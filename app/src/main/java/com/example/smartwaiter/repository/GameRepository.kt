package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Korisnik
import retrofit2.Response

class GameRepository {

    suspend fun setXp(
        table: String,
        method: String,
        id_korisnik: Int,
        iskustvo: Int
    ){
        return RetrofitInstance.api.setXp(table, method, id_korisnik, iskustvo)
    }
}