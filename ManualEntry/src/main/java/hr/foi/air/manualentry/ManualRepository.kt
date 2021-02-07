package hr.foi.air.manualentry

import com.example.database.UserPreferences

class ManualRepository (private val preferences: UserPreferences)  {


    suspend fun saveManualEntry(data: String) {
        preferences.saveManualEntry(data)
    }

}
