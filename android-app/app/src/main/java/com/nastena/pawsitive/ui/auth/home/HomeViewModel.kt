package com.nastena.pawsitive.ui.auth.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.data.datastore.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _logoutState = MutableStateFlow(false)
    val logoutState: StateFlow<Boolean> = _logoutState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            _logoutState.value = true
        }
    }
}
