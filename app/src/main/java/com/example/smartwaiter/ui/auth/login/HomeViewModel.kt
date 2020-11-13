package com.example.smartwaiter.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.AuthRepository
import hr.foi.air.webservice.model.Korisnik
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _myResponse: MutableLiveData<Response<List<Korisnik>>> = MutableLiveData()
    val myResponse: LiveData<Response<List<Korisnik>>>
        get() =  _myResponse


    fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ){
        viewModelScope.launch {
            val response = authRepository.getKorisnik(table,method,username, password)
            _myResponse.value = response
        }
    }

    suspend fun saveAuthToken(token: String)  {
        authRepository.saveAuthToken(token)
    }

    suspend fun saveUserType(userType: String){
        authRepository.saveUserType(userType)
    }

}