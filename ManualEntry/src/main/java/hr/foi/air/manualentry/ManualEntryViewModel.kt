package hr.foi.air.manualentry

import androidx.lifecycle.ViewModel

class ManualEntryViewModel (
    private val manRepository: ManualRepository
): ViewModel() {
    suspend fun saveManualEntry(data: String) {
        manRepository.saveManualEntry(data)
    }
}