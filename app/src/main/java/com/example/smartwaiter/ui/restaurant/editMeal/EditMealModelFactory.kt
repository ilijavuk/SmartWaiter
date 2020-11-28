package com.example.smartwaiter.ui.restaurant.editMeal


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.Add_mealRepository


class EditMealModelFactory(private val repository: Add_mealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditMealViewModel(repository) as T
    }
}