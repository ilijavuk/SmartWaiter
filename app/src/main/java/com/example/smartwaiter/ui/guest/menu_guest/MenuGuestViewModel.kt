package com.example.smartwaiter.ui.guest.menu_guest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource

import kotlinx.coroutines.launch
import retrofit2.Response

class MenuGuestViewModel(private val repository: Add_mealRepository) : ViewModel()
{
    val myResponse: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Resource<List<Tag>>> = MutableLiveData()


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
}