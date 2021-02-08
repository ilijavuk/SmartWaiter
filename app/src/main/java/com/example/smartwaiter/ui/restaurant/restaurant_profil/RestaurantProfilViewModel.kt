package com.example.smartwaiter.ui.restaurant.restaurant_profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.LvlRepository
import com.example.smartwaiter.repository.OrderRepository2
import hr.foi.air.webservice.model.Lvl
import hr.foi.air.webservice.model.Order2
import kotlinx.coroutines.launch
import retrofit2.Response

class RestaurantProfilViewModel(
    private val repository: LvlRepository
): ViewModel(){
    val myResponse: MutableLiveData<Response<List<Lvl>>> = MutableLiveData()

    fun getLvl(
        method: String,
    ){
        viewModelScope.launch {
            val response = repository.getLvl( method)
            myResponse.value = response
        }
    }
}
