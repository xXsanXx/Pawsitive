package com.nastena.pawsitive.ui.screens.register


data class RegisterEmailState(
    val text: String
)

sealed interface RegistrationEmailValidation : RegisterEmailState {

}