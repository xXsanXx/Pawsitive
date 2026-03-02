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

        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        if (trimmedEmail.isBlank() || trimmedPassword.isBlank()) {
            _state.value = LoginState.Error(LoginError.EmptyFields)
            return
        }

        if (!isValidEmail(trimmedEmail)) {
            _state.value = LoginState.Error(LoginError.InvalidEmail)
            return
        }

        if (trimmedPassword.length < 6) {
            _state.value = LoginState.Error(LoginError.WeakPassword)
            return
        }

        viewModelScope.launch {

            _state.value = LoginState.Loading

            val result = repository.login(trimmedEmail, trimmedPassword)

            _state.value = result.fold(
                onSuccess = { token ->
                    tokenManager.saveToken(token)
                    LoginState.Success
                },
                onFailure = { throwable ->
                    handleServerError(throwable)
                }
            )
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun handleServerError(throwable: Throwable): LoginState {

        val message = throwable.message ?: return LoginState.Error(LoginError.Unknown)

        return when {
            message.contains("401") ->
                LoginState.Error(LoginError.InvalidCredentials)

            message.contains("IOException") ->
                LoginState.Error(LoginError.NetworkError)

            else ->
                LoginState.Error(LoginError.ServerError(message))
        }
    }
}