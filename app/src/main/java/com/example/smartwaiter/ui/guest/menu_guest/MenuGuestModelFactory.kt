package com.example.smartwaiter.ui.guest.menu_guest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.guest.menu_guest.MenuGuestViewModel


class MenuGuestModelFactory(private val repository: Add_mealRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuGuestViewModel(repository) as T
    }
}