package com.example.smartwaiter.ui.restaurant.add_meal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository

import com.example.smartwaiter.repository.AddRestaurantRepository
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class Add_mealViewModel(private val repository: Add_mealRepository): ViewModel()  {

    val myResponse: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse2: MutableLiveData<Resource<List<Tag>>> = MutableLiveData()
    val myResponse3: MutableLiveData<Resource<String>> = MutableLiveData()
    val myResponse4: MutableLiveData<Resource<String>> = MutableLiveData()
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
    fun insertTag(
        table: String,
        method: String,
        tag: String,
    ){
        viewModelScope.launch {
            val response = repository.insertTag(
                table = table,
                method = method,
                tag = tag
            )
            myResponse3.value = response
        }
    }

    fun bindTag(
        table: String,
        method: String,
        stavka_id: String,
        tag_id: String
    ){
        viewModelScope.launch {
            val response = repository.bindTag(
                table = table,
                method = method,
                stavka_id = stavka_id,
                tag_id = tag_id
            )
            myResponse4.value = response
        }
    }

    fun getAllTags(
        table: String,
        method: String
    ){
        viewModelScope.launch {
            val response = repository.getAllTags(table,method)
            myResponse2.value = response
        }
    }

}