package com.example.smartwaiter.ui.guest.raterestaurant

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.AddRestaurantRepository
import com.example.smartwaiter.repository.RateRestaurantRepository
import com.example.smartwaiter.ui.restaurant.restaurant_list.RestaurantListViewModel

class RateRestaurantViewModelFactory(private val repository: RateRestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RateRestaurantViewModel(repository) as T
    }
}