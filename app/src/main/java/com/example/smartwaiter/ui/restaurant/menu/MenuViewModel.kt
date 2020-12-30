package com.example.smartwaiter.ui.restaurant.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class MenuViewModel(private val repository: Add_mealRepository) : ViewModel()
{
    val myResponse: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Resource<List<Tag>>> = MutableLiveData()
    val myResponse3: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()


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
    /*
    fun getAllTags(
        table: String,
        method: String
    ){
        viewModelScope.launch {
            val response = repository.getAllTags(table,method)
            myResponse2.value = response
        }
    }

     */
    fun tagsByRestaurant(
        method : String,
        lokal_id : String
    ){
        viewModelScope.launch {
            val response = repository.tagsByRestaurant(method, lokal_id)
            myResponse2.value = response
        }
    }

    fun setMealAvailability(
        table: String,
        method: String,
        mealId: String,
        available: String,
    ){
        viewModelScope.launch {
            val response = repository.setMealAvailability(
                table = table,
                method = method,
                mealId = mealId,
                available = available)

        }
    }
    fun menuByTag(
        method : String,
        id_tag: String,
        lokal_id : String
    ){
        viewModelScope.launch {
            val response = repository.menuByTag(method,id_tag, lokal_id)
            myResponse3.value = response
        }
    }

}