package com.nastena.pawsitive.ui.screens.user.details.form

import com.nastena.pawsitive.ui.common.validation.ValidationState

object FormState {

    data class Animal(
        val name: String
    )

    data class FullName(
        val text: String,
        val validation: ValidationState
    )

    data class Age(
        val text: String,
        val validation: ValidationState
    )

    data class Profession(
        val text: String,
        val validation: ValidationState
    )

    data class Phone(
        val text: String,
        val validation: ValidationState
    )

}