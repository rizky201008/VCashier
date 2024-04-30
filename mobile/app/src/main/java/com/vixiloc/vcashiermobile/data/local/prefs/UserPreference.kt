package com.vixiloc.vcashiermobile.data.local.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.vixiloc.vcashiermobile.commons.Strings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Strings.USER_PREFERENCES)

class UserPreference(private val dataStore: DataStore<Preferences>) {
    fun getApiKey(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(Strings.APIKEY_KEY)] ?: ""
        }
    }

    suspend fun saveApiKey(data: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(Strings.APIKEY_KEY)] = data
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