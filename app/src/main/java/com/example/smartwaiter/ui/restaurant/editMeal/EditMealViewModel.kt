package com.example.smartwaiter.ui.restaurant.editMeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal
import kotlinx.coroutines.launch
import retrofit2.Response

class EditMealViewModel(private val repository: Add_mealRepository): ViewModel() {
    val myResponse: MutableLiveData<Response<List<Meal>>> = MutableLiveData()


    fun getMealById(
        table: String,
        method: String,
        id_stavka: String,
    ){
        viewModelScope.launch {
            val response = repository.getMealById(table,method, id_stavka)
            myResponse.value = response
        }
    }
}