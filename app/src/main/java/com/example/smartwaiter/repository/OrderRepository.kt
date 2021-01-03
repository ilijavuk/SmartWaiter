package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Order
import hr.foi.air.webservice.model.TableOrder
import retrofit2.Response

class OrderRepository {

    suspend fun getOrders(
        //table: String,
        method: String,
        stol_id: String
    ):Response<List<Order>>{
        return RetrofitInstance.api.getOrders(method, stol_id)
    }
}