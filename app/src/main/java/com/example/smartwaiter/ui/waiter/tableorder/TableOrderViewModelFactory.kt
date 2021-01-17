package com.example.smartwaiter.ui.waiter.tablelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.OrderRepository


class TableOrderViewModelFactory(private val repository: OrderRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TableOrderViewModel(repository) as T
    }
}