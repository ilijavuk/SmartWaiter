package com.example.smartwaiter.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.RegisterRepository
import hr.foi.air.webservice.model.Korisnik
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(
    private val repository: RegisterRepository
) : ViewModel(){

    private val _myResponseUsername: MutableLiveData<Response<List<Korisnik>>> = MutableLiveData()
    val myResponseUsername: LiveData<Response<List<Korisnik>>>
        get() =  _myResponseUsername

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

    fun getUsername(
        table: String,
        method: String,
        username: String,
        operator: String,
        email: String,
    ){
        viewModelScope.launch {
            val response = repository.getUsername(table, method, username, operator, email)
            _myResponseUsername.value = response
        }
    }

}
