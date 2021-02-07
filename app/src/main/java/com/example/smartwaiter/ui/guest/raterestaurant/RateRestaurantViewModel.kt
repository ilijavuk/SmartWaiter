package com.example.smartwaiter.ui.guest.raterestaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.RateRestaurantRepository
import hr.foi.air.webservice.model.RestaurantRating
import hr.foi.air.webservice.model.Restoran
import kotlinx.coroutines.launch
import retrofit2.Response

class RateRestaurantViewModel(private val repository: RateRestaurantRepository) : ViewModel() {

    val myResponse: MutableLiveData<Response<List<RestaurantRating>>> = MutableLiveData()
    val myResponse2: MutableLiveData<Response<List<Restoran>>> = MutableLiveData()

    fun pushRating(
        method: String,
        user_id: String,
        lokal_id: String,
        rating: String
    ){
        viewModelScope.launch {
            val response = repository.pushRating("Ocjena",method,user_id,lokal_id,rating)
        }
    }

    fun getRating(
        user_id: String,
        lokal_id: String
    ) {
        viewModelScope.launch {
            val response = repository.getRating("Ocjena","select",user_id,lokal_id)
            myResponse.value = response
        }
    }

    fun getRestaurantName(
        lokal_id: String
    ){
        viewModelScope.launch {
            val response = repository.getRestaurantName(lokal_id)
            myResponse2.value = response
        }
    }

}