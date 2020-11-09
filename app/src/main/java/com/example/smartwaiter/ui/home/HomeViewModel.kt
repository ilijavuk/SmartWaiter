package com.example.smartwaiter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Repository
import hr.foi.air.webservice.model.Korisnik
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    val myResponse: MutableLiveData<Response<List<Korisnik>>> = MutableLiveData()

    fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ){
        viewModelScope.launch {
            val response = repository.getKorisnik(table,method,username, password)
            myResponse.value = response

        }
    }


}