package com.example.tfg.model.security

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureTokenStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            File(context.filesDir, "secure_tokens.preferences_pb")
        }
    )

    private fun key(name: String) = stringPreferencesKey(name)
    private fun ivKey(name: String) = stringPreferencesKey("${name}_iv")

    suspend fun save(name: String, value: String) {
        val (encrypted, iv) = CryptoManager.encrypt(value)
        dataStore.edit {
            it[key(name)] = Base64.encodeToString(encrypted, Base64.DEFAULT)
            it[ivKey(name)] = Base64.encodeToString(iv, Base64.DEFAULT)
        }
    }

    suspend fun read(name: String): String? {
        val prefs = dataStore.data.first()
        val enc = prefs[key(name)] ?: return null
        val iv = prefs[ivKey(name)] ?: return null
        return CryptoManager.decrypt(
            Base64.decode(enc, Base64.DEFAULT),
            Base64.decode(iv, Base64.DEFAULT)
        )
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}