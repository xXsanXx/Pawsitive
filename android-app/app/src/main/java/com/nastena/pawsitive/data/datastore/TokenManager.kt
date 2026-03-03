package com.nastena.pawsitive.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.nastena.pawsitive.data.remote.dto.Role
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class TokenManager(private val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val ROLE_KEY = stringPreferencesKey("role")


    suspend fun saveToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun saveRole(role: Role) {
        context.dataStore.edit {
            it[ROLE_KEY] = role.name
        }
    }

    suspend fun getToken(): String? {
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    suspend fun getRole(): Role? {
        val prefs = context.dataStore.data.first()
        return prefs[ROLE_KEY]?.let { Role.valueOf(it)}
    }

    suspend fun clearToken() {
        context.dataStore.edit {
           it.clear()
        }
    }




}
