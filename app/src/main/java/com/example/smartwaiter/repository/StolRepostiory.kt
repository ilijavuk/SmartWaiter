package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance

class StolRepostiory : BaseRepository(){

    suspend fun getTableByHash(
        table: String,
        method: String,
        hash: String
    )= safeApiCall {
        RetrofitInstance.api.getTableFromHash(table, method, hash)
    }

}