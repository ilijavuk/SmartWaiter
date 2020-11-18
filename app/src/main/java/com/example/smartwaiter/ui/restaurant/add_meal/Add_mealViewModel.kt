package com.example.smartwaiter.ui.restaurant.add_meal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository

import com.example.smartwaiter.repository.AddRestaurantRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class Add_mealViewModel(private val repository: Add_mealRepository): ViewModel()  {

    val myResponse: MutableLiveData<Response<String>> = MutableLiveData()

    fun insertMeal(
        table: String,
        method: String,
        mealName: String,
        mealPrice: String,
        mealDescription: String,
        mealPhotoPath: String,
        lokalId: String,
    ){
        viewModelScope.launch {
            val response = repository.insertMeal(
                table = table,
                method = method,
                mealName = mealName,
                mealPrice = mealPrice,
                mealDescription = mealDescription,
                mealPhotoPath = mealPhotoPath,
                lokalId = lokalId
            )
            myResponse.value = response
        }
    }
}