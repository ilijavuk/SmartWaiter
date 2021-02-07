package com.example.smartwaiter.ui.guest.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Add_mealRepository
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Restoran
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch


class MapViewModel(private val repository: Add_mealRepository): ViewModel() {
    val myResponse: MutableLiveData<Resource<List<Restoran>>> = MutableLiveData()


    fun getRestorani(
        tablica: String,
        metoda: String,
    ) {
        viewModelScope.launch {
            val response = repository.getRestorani(
                tablica,
                metoda
            )
            myResponse.value = response
        }

    }
}