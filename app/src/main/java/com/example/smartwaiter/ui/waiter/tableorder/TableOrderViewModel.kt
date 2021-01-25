package com.example.smartwaiter.ui.waiter.tablelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.OrderRepository
import com.example.smartwaiter.repository.OrderRepository2
import com.example.smartwaiter.repository.TableOrderRepository
import hr.foi.air.webservice.model.Order
import hr.foi.air.webservice.model.Order2
import hr.foi.air.webservice.model.TableOrder
import kotlinx.coroutines.launch
import retrofit2.Response

class TableOrderViewModel(
    private val repository: OrderRepository2
):ViewModel(){
    val myResponse: MutableLiveData<Response<List<Order2>>> = MutableLiveData()

    fun getOrders(
        //table: String,
        method: String,
        stol_id: String
    ){
        viewModelScope.launch {
            val response = repository.getOrders( method, stol_id)
            myResponse.value = response
        }
    }
}