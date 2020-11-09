package com.example.smartwaiter.ui.restaurant_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.Repository

class RestaurantListModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantListViewModel(repository) as T
    }
}