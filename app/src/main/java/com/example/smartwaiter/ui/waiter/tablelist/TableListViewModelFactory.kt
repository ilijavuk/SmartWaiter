package com.example.smartwaiter.ui.waiter.tablelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.TableOrderRepository

class TableListViewModelFactory(private val repository: TableOrderRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TableListViewModel(repository) as T
    }
}