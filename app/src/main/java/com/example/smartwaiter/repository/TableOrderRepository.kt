package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.TableOrder
import retrofit2.Response

class TableOrderRepository {

    suspend fun getTables(
        table: String,
        method: String,
    ):Response<List<TableOrder>>{
        return RetrofitInstance.api.getTables(table, method)
    }
}