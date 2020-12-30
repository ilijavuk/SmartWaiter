package com.example.smartwaiter.ui.guest.menudetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.OrderRepository
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch

class MenuDetailsViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _myResponse: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse: LiveData<Resource<String>>
        get() = _myResponse


    fun saveMeal(meal: Meal) = viewModelScope.launch {
        orderRepository.upsert(meal)
    }

    fun getMeals() = orderRepository.getMeals()

    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        orderRepository.deleteMeal(meal)
    }
}