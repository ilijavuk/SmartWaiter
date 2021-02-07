package com.example.smartwaiter.ui.guest.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.GameRepository
import com.example.smartwaiter.repository.RegisterRepository
import com.example.smartwaiter.ui.auth.register.RegisterViewModel

class GameViewModelFactory(private val repository: GameRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GameViewModel(repository) as T
    }
}