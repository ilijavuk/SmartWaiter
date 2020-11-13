package com.example.smartwaiter.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: RegisterRepository
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
