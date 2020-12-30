package com.example.smartwaiter.ui.restaurant.qr

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.StolRepostiory


class QrModelFactory(private val repository: StolRepostiory) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QrViewModel(repository) as T
    }
}