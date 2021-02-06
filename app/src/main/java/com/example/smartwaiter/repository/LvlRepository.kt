package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Lvl
import hr.foi.air.webservice.model.Order2

class LvlRepository {

    suspend fun getLvl(
        method: String,
    ): retrofit2.Response<List<Lvl>> {
        return RetrofitInstance.api.getLvl(method)
    }

}