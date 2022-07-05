package com.example.giftcardshop.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.giftcardshop.domain.model.AuthStatus
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "AuthDataStore")

@ViewModelScoped
class AuthDataStore(context: Context){
    private object AuthKeys {
        val usernameKey = stringPreferencesKey(name = "username_key")
        val passwordKey = stringPreferencesKey(name = "password_key")
        val statusKey = stringPreferencesKey(name = "status_key")
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun persistAuthStatus(authStatus: AuthStatus) {
        if (authStatus.username.isNullOrBlank() || authStatus.password.isNullOrBlank()) {
            throw Exception("Empty status exception")
        }
        dataStore.edit {
            it[AuthKeys.usernameKey] = authStatus.username
            it[AuthKeys.passwordKey] = authStatus.password
            it[AuthKeys.statusKey] = authStatus.authStatus.toString()
        }
    }

    suspend fun clearAuthPersistence() {
        dataStore.edit { it.clear() }
    }

    val getCurrentAuthStatus: Flow<AuthStatus> = dataStore.data
        .catch { e ->
            if (e is IOException) { emit(emptyPreferences()) }
            else { throw e } }
        .map { p ->
            AuthStatus(
                username = p[AuthKeys.usernameKey],
                password = p[AuthKeys.passwordKey],
                authStatus = p[AuthKeys.statusKey].toBoolean()
            )
        }
}