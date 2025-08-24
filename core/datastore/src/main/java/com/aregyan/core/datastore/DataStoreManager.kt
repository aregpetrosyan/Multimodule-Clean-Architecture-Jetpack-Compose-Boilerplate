package com.aregyan.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_datastore")
    }

    private val dataStore = context.dataStore

    /**
     * Writes a value to the DataStore for the given key.
     */
    suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * Reads a value from the DataStore for the given key.
     * Emits [default] if the key is not present.
     */
    fun <T> read(key: Preferences.Key<T>, default: T): Flow<T> {
        return dataStore.data.map { preferences ->
            preferences[key] ?: default
        }
    }

    /**
     * Removes a key-value pair from the DataStore.
     */
    suspend fun <T> remove(key: Preferences.Key<T>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    /**
     * Clears all preferences from the DataStore.
     */
    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }

    /**
     * Returns the raw [Preferences] flow from the DataStore.
     */
    fun readRaw(): Flow<Preferences> = dataStore.data
}