package com.example.smartwaiter.ui.restaurant.restaurant_profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.LvlRepository
import com.example.smartwaiter.ui.waiter.tablelist.TableOrderViewModel

class RestaurantProfilViewModelFactory(private val repository: LvlRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RestaurantProfilViewModel(repository) as T
    }
}