package com.example.smartwaiter.ui.restaurant.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal

import kotlinx.coroutines.launch
import retrofit2.Response

class MenuViewModel(private val repository: Add_mealRepository) : ViewModel()
{
    val myResponse: MutableLiveData<Response<List<Meal>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<String>> = MutableLiveData()

    fun getMeal(
        table: String,
        method: String,
        lokal_id: String,
    ){
        viewModelScope.launch {
            val response = repository.getMeal(table,method, lokal_id)
            myResponse.value = response
        }
    }
    fun setMealAvailability(
        table: String,
        method: String,
        mealId: String,
        available: String,
    ){
        viewModelScope.launch {
            val response = repository.setMealAvailability(table,method, mealId, available)
            myResponse2.value = response
        }
    }
}