package com.nastena.pawsitive.ui.screens.register

import com.nastena.pawsitive.dto.AccountRole

internal sealed interface RegisterViewEvents {
    sealed interface Email : RegisterViewEvents {
        data class TextUpdated(val newText: String) : Email
    }

    sealed interface Password : RegisterViewEvents {
        data class TextUpdate(val newText: String) : Password
        object EyeClicked : Password
    }

    object RegisterClicked : RegisterViewEvents
    object GoToLoginClicked : RegisterViewEvents
}