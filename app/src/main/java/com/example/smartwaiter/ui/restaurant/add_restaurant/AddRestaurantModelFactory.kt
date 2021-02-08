package com.example.smartwaiter.ui.restaurant.add_restaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.AddRestaurantRepository

class AddRestaurantModelFactory(private val repository: AddRestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddRestaurantViewModel(repository) as T
    }
}