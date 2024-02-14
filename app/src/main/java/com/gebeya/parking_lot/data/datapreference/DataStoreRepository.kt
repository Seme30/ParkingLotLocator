package com.gebeya.parking_lot.data.datapreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gebeya.parking_lot.data.network.model.PhoneVerifyResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "on_boarding_pref")

class DataStoreRepository(context: Context) {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = "on_boarding_completed")
        val authenticationTokenKey = stringPreferencesKey(name = "authentication_token")
    }

    private val dataStore = context.dataStore
    private val gson = Gson()

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

    suspend fun saveAuthenticationToken(authenticationToken: PhoneVerifyResponse) {
        val tokenJson = gson.toJson(authenticationToken)
        println("Login result json: $tokenJson")
        dataStore.edit { preferences ->
            preferences[PreferencesKey.authenticationTokenKey] = tokenJson
        }
    }

    fun getAuthenticationToken(): Flow<PhoneVerifyResponse?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val tokenJson = preferences[PreferencesKey.authenticationTokenKey]
                if(tokenJson != null){
                    gson.fromJson(tokenJson, PhoneVerifyResponse::class.java)
                }else{
                    null
                }
            }
    }
}
