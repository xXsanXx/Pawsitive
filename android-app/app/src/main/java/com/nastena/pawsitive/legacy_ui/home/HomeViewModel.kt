package com.nastena.pawsitive.legacy_ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.repository.datastores.AuthDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _logoutState = MutableStateFlow(false)
    val logoutState: StateFlow<Boolean> = _logoutState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            authDataStore.clearToken()
            _logoutState.value = true
        }
    }
}
