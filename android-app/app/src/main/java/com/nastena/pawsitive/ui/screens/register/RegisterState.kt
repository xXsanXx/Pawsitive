package com.nastena.pawsitive.ui.screens.register

import com.nastena.pawsitive.dto.AccountRole

object RegisterState {
    data class Email(
        val text: String,
        val validation: Validation
    ) {
        sealed interface Validation {
            object Valid: Validation
            object InvalidFormat: Validation
            object Empty: Validation
        }
    }

    data class Password(
        val text: String,
        val validation: Validation
    ) {
        sealed interface Validation {
            object Valid: Validation
            object InvalidFormat: Validation
            object Empty: Validation
        }
    }

    data class ConfirmPassword(
        val text: String,
        val isValid: Boolean
    )

    data class AccountRoleMenu(
        val isExpended: Boolean,
        val selected: AccountRole?,
        val isValid: Boolean
    )
}

