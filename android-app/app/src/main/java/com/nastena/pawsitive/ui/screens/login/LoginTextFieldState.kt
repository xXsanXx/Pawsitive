package com.nastena.pawsitive.ui.screens.login

internal sealed class LoginTextFieldState(
    open val text: String,
    open val isValid: Boolean
) {

    data class Email(
        override val text: String,
        override val isValid: Boolean
    ) : LoginTextFieldState(text, isValid) {
        override fun copy(text: String?, isValid: Boolean?): LoginTextFieldState =
            this.copy(text = text ?: this.text, isValid ?: this.isValid)
    }

    data class Password(
        override val text: String,
        override val isValid: Boolean,
        val isVisible: Boolean
    ) : LoginTextFieldState(text, isValid) {
        override fun copy(text: String?, isValid: Boolean?): LoginTextFieldState =
            this.copy(text = text ?: this.text, isValid ?: this.isValid)
    }

    abstract fun copy(text: String? = null, isValid: Boolean? = null) : LoginTextFieldState
}