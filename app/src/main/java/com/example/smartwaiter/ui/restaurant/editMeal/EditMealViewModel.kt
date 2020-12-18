package com.example.smartwaiter.ui.restaurant.editMeal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class EditMealViewModel(private val repository: Add_mealRepository): ViewModel() {
    val myResponse: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()


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

    fun updateMeal(
        table: String,
        method: String,
        mealId: String,
        mealName: String,
        mealPrice: String,
        mealDescription: String,
        mealPhotoPath: String,
    ){
        viewModelScope.launch {
            val response = repository.updateMeal(
                table = table,
                method = method,
                mealId = mealId,
                mealName = mealName,
                mealPrice = mealPrice,
                mealDescription = mealDescription,
                mealPhotoPath = mealPhotoPath,
            )
        }
    }
}