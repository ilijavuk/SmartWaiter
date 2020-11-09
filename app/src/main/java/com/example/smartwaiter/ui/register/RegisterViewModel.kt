package com.example.smartwaiter.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.Repository
import hr.foi.air.webservice.model.Korisnik
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(
    private val repository: Repository
) : ViewModel(){

    fun RegisterKorisnik(
        table: String,
        method: String,
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        userType: String,
        password: String
    ){
        viewModelScope.launch {
            repository.RegisterKorisnik(table,method,username, firstName, lastName, email, userType, password)
        }
    }
}
