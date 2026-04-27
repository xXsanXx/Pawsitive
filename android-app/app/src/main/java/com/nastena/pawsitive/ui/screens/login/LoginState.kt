package com.nastena.pawsitive.ui.screens.login

import com.nastena.pawsitive.ui.common.validation.ValidationState

object LoginState {

    data class Email(
        val text: String,
        val validation: ValidationState
    )

    data class Password(
        val text: String,
        val validation: ValidationState,
        val isVisible: Boolean
    )

}