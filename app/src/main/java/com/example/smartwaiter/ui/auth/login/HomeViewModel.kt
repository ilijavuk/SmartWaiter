package com.example.smartwaiter.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.AuthRepository
import hr.foi.air.webservice.model.Korisnik
import hr.foi.air.webservice.util.Resource
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _myResponse: MutableLiveData<Resource<List<Korisnik>>> = MutableLiveData()
    val myResponse: LiveData<Resource<List<Korisnik>>>
        get() = _myResponse

    fun getKorisnik(
        table: String,
        method: String,
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            _myResponse.value = Resource.Loading
            val response = authRepository.getKorisnik(table, method, username, password)
            _myResponse.value = response
        }
    }

    suspend fun saveAuthToken(token: String) {
        authRepository.saveAuthToken(token)
    }

    suspend fun saveUserType(userType: String) {
        authRepository.saveUserType(userType)
    }
}