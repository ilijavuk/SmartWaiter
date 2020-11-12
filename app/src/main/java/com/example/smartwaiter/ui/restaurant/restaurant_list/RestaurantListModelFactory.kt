package com.example.smartwaiter.ui.restaurant.restaurant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.AddRestaurantRepository

class RestaurantListModelFactory(private val repository: AddRestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantListViewModel(repository) as T
    }
}