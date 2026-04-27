package com.nastena.pawsitive.ui.screens.login

internal sealed interface LoginEvents {
    sealed interface Email : LoginEvents {
        data class TextUpdated(val newText: String) : Email
    }

    sealed interface Password : LoginEvents {
        data class TextUpdated(val newText: String) : Password
        object EyeClicked : Password
    }

    object GoToRegistrationClicked : LoginEvents
    object LoginClicked : LoginEvents
}