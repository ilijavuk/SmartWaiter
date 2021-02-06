package com.example.smartwaiter.ui.guest.nfc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.repository.StolRepostiory


class NfcModelFactory(private val repository: StolRepostiory) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NfcViewModel(repository) as T
    }
}