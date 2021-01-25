package com.example.smartwaiter.ui.waiter.tablelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.OrderRepository
import com.example.smartwaiter.repository.OrderRepository2


class TableOrderViewModelFactory(private val repository: OrderRepository2) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TableOrderViewModel(repository) as T
    }
}