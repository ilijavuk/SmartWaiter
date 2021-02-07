package hr.foi.air.manualentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ManualEntryModelFactory(private val repository: ManualRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ManualEntryViewModel(repository) as T
    }
}