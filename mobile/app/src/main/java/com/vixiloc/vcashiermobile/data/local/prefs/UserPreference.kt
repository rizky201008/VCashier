package com.vixiloc.vcashiermobile.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vixiloc.vcashiermobile.utils.Strings
import com.vixiloc.vcashiermobile.utils.Strings.APIKEY_KEY
import com.vixiloc.vcashiermobile.utils.Strings.ROLE_KEY
import com.vixiloc.vcashiermobile.utils.Strings.USER_PREFERENCES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

class UserPreference(private val dataStore: DataStore<Preferences>) {
    fun getApiKey(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(APIKEY_KEY)] ?: ""
        }
    }

    suspend fun saveApiKey(data: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(APIKEY_KEY)] = data
        }
    }

    fun getRole(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(ROLE_KEY)] ?: ""
        }
    }

    suspend fun saveRole(data: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(ROLE_KEY)] = data
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}