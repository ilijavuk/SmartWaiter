package com.example.smartwaiter.ui.guest.menuguest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.smartwaiter.repository.Add_mealRepository


class MenuGuestModelFactory(private val repository: Add_mealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuGuestViewModel(repository) as T
    }
}