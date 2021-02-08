package com.example.smartwaiter.ui.guest.menuguest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource

import kotlinx.coroutines.launch

class MenuGuestViewModel(private val repository: Add_mealRepository) : ViewModel()
{
    val myResponse: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Resource<List<Tag>>> = MutableLiveData()
    val myResponse3: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()

    var dbMeal: MutableLiveData<List<Meal>> = MutableLiveData()

    init {
        getMeal(table = "Stavka_jelovnika", method = "select", "1")
    }

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