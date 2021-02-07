package com.example.smartwaiter.repository

import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Order
import hr.foi.air.webservice.model.Order2

class OrderRepository2{

    suspend fun getOrders(
        //table: String,
        method: String,
        stol_id: String
    ): retrofit2.Response<List<Order2>> {
        return RetrofitInstance.api.getOrders(method, stol_id)
    }

    suspend fun setOrders(
        table: String,
        method: String,
        id_stol: Int,
        rezerviran: Int
    ){
        return RetrofitInstance.api.setOrders(table, method, id_stol, rezerviran)
    }


}