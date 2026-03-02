package com.nastena.pawsitive.ui.auth.login

import com.nastena.pawsitive.ui.auth.register.RegisterError

sealed class LoginState {
    object Idle : LoginState()

    object Loading : LoginState()

    object Success: LoginState()

    data class Error(val error: LoginError) : LoginState()
}