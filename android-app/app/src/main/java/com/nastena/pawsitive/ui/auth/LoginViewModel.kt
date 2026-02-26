package com.nastena.pawsitive.ui.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        _state.value = LoginState.Loading

        if (email == "test@test.com" && password == "1234") {
            _state.value = LoginState.Success("fake_jwt_token")
        } else {
            _state.value = LoginState.Error("Wrong email or password")
        }
    }
}