package com.example.smartwaiter.ui.guest.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.database.db.models.OrderedMeal
import com.example.smartwaiter.repository.OrderRepository
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch

class OrderViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _myResponse: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse: LiveData<Resource<String>>
        get() = _myResponse

    fun saveOrderBucket(show: Boolean) = viewModelScope.launch {
        orderRepository.saveOrderBucket(show)
    }

    /********************* LOCAL DATABASE **************************/

    fun saveOrderedMeal(orderedMeal: OrderedMeal) = viewModelScope.launch {
        orderRepository.upsert(orderedMeal)
    }

    fun getOrderedMeals() = orderRepository.getMeals()

    fun deleteOrderedMeal(orderedMeal: OrderedMeal) = viewModelScope.launch {
        orderRepository.deleteMeal(orderedMeal)
    }

    fun deleteAllFromOrder() = viewModelScope.launch {
        orderRepository.deleteAllFromOrder()
    }

    /********************* REMOTE DATABASE **************************/

    fun makeOrder(
        table: String,
        method: String,
        user_id: Int,
        table_id: Int,
        status: Int,
        time: String,
        meal_id: Int,
        amount: Int
    ) {
        viewModelScope.launch {
            _myResponse.value = Resource.Loading
            val response = orderRepository.makeOrder(
                table,
                method,
                user_id,
                table_id,
                status,
                time,
                meal_id,
                amount
            )
            _myResponse.value = response
        }
    }

    fun sendNotification(
        lokal_id: Int,
    ) {
        viewModelScope.launch {
            orderRepository.pushNotification(lokal_id)
        }
    }

}