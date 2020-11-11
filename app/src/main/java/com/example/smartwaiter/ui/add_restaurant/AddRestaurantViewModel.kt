package com.example.smartwaiter.ui.add_restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.AddRestaurantRepository
import com.google.gson.Gson
import hr.foi.air.webservice.model.Korisnik
import kotlinx.coroutines.launch
import retrofit2.Response

class AddRestaurantViewModel(private val repository: AddRestaurantRepository): ViewModel() {

    val myResponse: MutableLiveData<Response<String>> = MutableLiveData()

    fun insertRestoran(
        table: String,
        method: String,
        restaurantName: String,
        restaurantAddress: String,
        GPS_Longitude: Double,
        GPS_Latitude: Double,
    ){
        viewModelScope.launch {
            val response = repository.insertRestoran(table,method,restaurantName,restaurantAddress, GPS_Longitude, GPS_Latitude)
            myResponse.value = response
        }
    }

}