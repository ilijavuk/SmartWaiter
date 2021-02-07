package com.example.smartwaiter.ui.guest.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartwaiter.repository.GameRepository
import com.example.smartwaiter.repository.RegisterRepository
import hr.foi.air.webservice.model.Korisnik
import kotlinx.coroutines.launch
import retrofit2.Response

class GameViewModel(
    private val repository: GameRepository
) : ViewModel(){

    fun setXp(
        table: String,
        method: String,
        id_korisnik: Int,
        iskustvo: Int
    ){
        viewModelScope.launch {
            repository.setXp(table,method,id_korisnik, iskustvo)
        }
    }
}