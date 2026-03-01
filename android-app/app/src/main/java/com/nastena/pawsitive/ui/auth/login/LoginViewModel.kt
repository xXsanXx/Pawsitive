package com.nastena.pawsitive.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.data.datastore.TokenManager
import com.nastena.pawsitive.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading

            val result = repository.login(email, password)

            _state.value = result.fold(
                onSuccess = { token ->
                    tokenManager.saveToken(token)
                    LoginState.Success(token)
                },
                onFailure = { error ->
                    LoginState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
}