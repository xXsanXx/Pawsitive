package com.nastena.pawsitive.repository.datastores

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nastena.pawsitive.dto.AccountRole
import kotlinx.coroutines.flow.first

private val Context.authDataStore by preferencesDataStore(name = "auth_prefs")

class AuthDataStore(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val ROLE_KEY = stringPreferencesKey("role")


    suspend fun saveToken(token: String) {
        context.authDataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun saveRole(role: AccountRole) {
        context.authDataStore.edit {
            it[ROLE_KEY] = role.name
        }
    }

    suspend fun getToken(): String? {
        val prefs: Preferences = context.authDataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    suspend fun getRole(): AccountRole? {
        val prefs = context.authDataStore.data.first()
        return prefs[ROLE_KEY]?.let { AccountRole.valueOf(it)}
    }

    suspend fun clearAll() {
        context.authDataStore.edit {
           it.clear()
        }
    }




}
