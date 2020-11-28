package com.example.smartwaiter.ui.restaurant.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.smartwaiter.repository.Add_mealRepository


class MenuModelFactory(private val repository: Add_mealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuViewModel(repository) as T
    }
}