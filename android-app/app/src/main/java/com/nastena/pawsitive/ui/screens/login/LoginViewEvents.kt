package com.nastena.pawsitive.ui.screens.login

internal sealed interface LoginViewEvents {
    sealed interface Email : LoginViewEvents {
        data class TextUpdated(val newText: String) : Email
    }

    sealed interface Password : LoginViewEvents {
        data class TextUpdate(val newText: String) : Password
        object EyeClicked : Password
    }

    object GoToRegistrationClicked : LoginViewEvents
    object LoginClicked : LoginViewEvents
}