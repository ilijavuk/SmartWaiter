package com.example.smartwaiter.ui.guest.menudetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.OrderRepository

class MenuDetailsViewModelFactory(private val orderRepository: OrderRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuDetailsViewModel(orderRepository) as T
    }
}