package com.example.smartwaiter.ui.restaurant.add_meal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.Add_mealRepository


class Add_mealModelFactory(private val repository: Add_mealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Add_mealViewModel(repository) as T
    }
}
