package id.anantyan.utils.sharedPreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

class DataStoreManager(
    private val context: Context
) {
    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore by preferencesDataStore(DATASTORE_SETTINGS)
        private val LOG_IN = booleanPreferencesKey("ID_USERS")
        private val USR_ID = intPreferencesKey("NAMA_JABATAN")
    }

    fun setLogIn(value: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.setValue(LOG_IN, value)
        }
    }

    fun setUserId(value: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            context.dataStore.setValue(USR_ID, value)
        }
    }

    fun getLogIn() = runBlocking { context.dataStore.getValue(LOG_IN, false).firstOrNull() }
    fun getUserId() = runBlocking { context.dataStore.getValue(USR_ID, -1).firstOrNull() }
}

fun <T> DataStore<Preferences>.getValue(
    key: Preferences.Key<T>,
    defaultValue: T
) = this.data
    .catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[key] ?: defaultValue
    }

suspend fun <T> DataStore<Preferences>.setValue(key: Preferences.Key<T>, value: T) {
    this.edit { preferences ->
        preferences[key] = value
    }
}

suspend fun <T> DataStore<Preferences>.removeValue(key: Preferences.Key<T>) {
    this.edit { preferences ->
        preferences.remove(key)
    }
}

suspend fun DataStore<Preferences>.clear() {
    this.edit { preferences ->
        preferences.clear()
    }
}