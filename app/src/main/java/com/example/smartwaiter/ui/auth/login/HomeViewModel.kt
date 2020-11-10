package com.example.smartwaiter.ui.auth.login

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

    val myResponse: MutableLiveData<Response<List<Korisnik>>> = MutableLiveData()

    fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ){
        viewModelScope.launch {
            val response = authRepository.getKorisnik(table,method,username, password)
            myResponse.value = response
        }
    }

    suspend fun saveAuthToken(token: String)  {
        authRepository.saveAuthToken(token)
    }

}