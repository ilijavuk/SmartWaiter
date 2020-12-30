package com.example.smartwaiter.ui.guest.menuguest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.model.Meal
import hr.foi.air.webservice.util.Resource

import kotlinx.coroutines.launch

class MenuGuestViewModel(private val repository: Add_mealRepository) : ViewModel()
{
    val myResponse: MutableLiveData<Resource<List<Meal>>> = MutableLiveData()

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
}