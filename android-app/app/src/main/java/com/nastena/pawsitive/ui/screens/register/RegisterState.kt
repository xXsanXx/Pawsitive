package com.nastena.pawsitive.ui.screens.register


sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val error: RegisterError) : RegisterState()
}