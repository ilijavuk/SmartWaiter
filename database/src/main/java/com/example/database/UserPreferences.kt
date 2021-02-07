package com.example.database

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences(
    context: Context
) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "smartWaiter_data_store"
        )
    }

    val authToken: Flow<String?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_AUTH]
    }

    val userType: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[USER_TYPE]
        }

    val orderBucket: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[ORDER_BUCKET]
        }


    val activeRestaurant: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[ACTIVE_RESTAURANT]
        }

    val totalCost: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[TOTAL_COST]
        }

    val customerID: Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[CUSTOMER_ID]
        }

    suspend fun saveAuthToken(authToken: String) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveUserType(userType: String) {
        dataStore.edit { preferences ->
            preferences[USER_TYPE] = userType
        }
    }

    suspend fun saveOrderBucket(orderBucket: Boolean) {
        dataStore.edit { preferences ->
            preferences[ORDER_BUCKET] = orderBucket
            }
    }
    
    suspend fun saveActiveRestaurant(activeRestaurant: String) {
        dataStore.edit { preferences ->
            preferences[ACTIVE_RESTAURANT] = activeRestaurant
        }
    }

    suspend fun saveTotalCost(totalCost: String) {
        dataStore.edit { preferences ->
            preferences[TOTAL_COST] = totalCost
        }
    }

    suspend fun saveCustomerID(customerID: String) {
        dataStore.edit { preferences ->
            preferences[CUSTOMER_ID] = customerID
        }
    }

    suspend fun clear(){
        dataStore.edit {  preferences ->
             preferences.clear()
        }
    }

    companion object {
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val USER_TYPE = preferencesKey<String>("type_user")
        private val ORDER_BUCKET = preferencesKey<Boolean>("order_bucket")
        private val ACTIVE_RESTAURANT = preferencesKey<String>("active_restaurant")
        private val TOTAL_COST = preferencesKey<String>("total_cost")
        private val CUSTOMER_ID = preferencesKey<String>("customer_id")
    }


}