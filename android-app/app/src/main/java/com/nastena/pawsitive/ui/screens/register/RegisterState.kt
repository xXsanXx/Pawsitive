package com.nastena.pawsitive.ui.screens.register

import com.nastena.pawsitive.dto.AccountRole
import com.nastena.pawsitive.ui.common.validation.ValidationState

object RegisterState {

    data class Name(
        val text: String,
        val validation: ValidationState
    )
    data class Email(
        val text: String,
        val validation: ValidationState
    )

    data class Password(
        val text: String,
        val validation: ValidationState
    )

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

