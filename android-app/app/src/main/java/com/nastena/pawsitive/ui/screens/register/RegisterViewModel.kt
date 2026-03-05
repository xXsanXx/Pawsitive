package com.nastena.pawsitive.ui.screens.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.repository.AccountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AccountRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun register(email: String,
                 password: String,
                 role: AccountRole
    ) {
        val trimmedEmail = email.trim()
        val trimmedPassword = password.trim()

        if (trimmedEmail.isBlank() || trimmedPassword.isBlank()) {
            _state.value = RegisterState.Error(RegisterError.EmptyFields)
            return
        }

        if (!isValidEmail(trimmedEmail)) {
            _state.value = RegisterState.Error(RegisterError.InvalidEmail)
            return
        }

        if (!isValidPassword(trimmedPassword)) {
            _state.value = RegisterState.Error(RegisterError.WeakPassword)
            return
        }

        if (role != AccountRole.USER && role != AccountRole.SHELTER) {
            _state.value = RegisterState.Error(RegisterError.InvalidRole)
            return
        }

        viewModelScope.launch {
            _state.value = RegisterState.Loading

            val result = repository.register(
                trimmedEmail,
                trimmedPassword,
                role
            )

            _state.value = result.fold(
                onSuccess = {
                    RegisterState.Success
                },
                onFailure = { throwable ->
                    handleServerError(throwable)
                }
            )
        }
    }

    private fun isValidEmail(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS
            .matcher(email)
            .matches()
    }

    private fun isValidPassword(password: String) : Boolean {
        if (password.length < 6) return false

        val hasDigit = password.any { it.isDigit() }

        val hasLetter = password.any { it.isLetter() }

        return hasDigit && hasLetter
    }

    private fun handleServerError(throwable: Throwable): RegisterState {
        val message = throwable.message ?: return RegisterState.Error(RegisterError.Unknown)

        return when {
            message.contains("409") ->
                RegisterState.Error(RegisterError.EmailAlreadyExists)

            message.contains("400") ->
                RegisterState.Error(RegisterError.ServerError("Некорректные данные"))

            else ->
                RegisterState.Error(RegisterError.ServerError(message))
        }
    }



}
