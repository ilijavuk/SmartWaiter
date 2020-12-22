package com.example.smartwaiter.ui.guest.menudetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.OrderRepository
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch

class MenuDetailsViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _myResponse: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse: LiveData<Resource<String>>
        get() = _myResponse

    fun makeOrder(
        table: String,
        method: String,
        user_id: String,
        restaurantTable_id: String,
        meal_id: String,
        amount: String,
        time: String,
    ) {
        viewModelScope.launch {
            _myResponse.value = Resource.Loading
            val response = orderRepository.makeOrder(table, method, user_id, restaurantTable_id, meal_id ,amount, time)
            _myResponse.value = response
        }
    }
}