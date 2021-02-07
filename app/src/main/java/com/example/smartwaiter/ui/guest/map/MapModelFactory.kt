package com.example.smartwaiter.ui.guest.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.Add_mealRepository



class MapModelFactory(private val repository: Add_mealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(repository) as T
    }
}