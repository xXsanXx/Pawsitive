package com.nastena.pawsitive.ui.screens.user.details.form

import com.nastena.pawsitive.ui.common.validation.ValidationState

object FormState {

    data class AnimalInfo(
        val animalName: String,
        val shelterName: String
    )

    data class FullName(
        val text: String,
        val validation: ValidationState
    )

    data class BirthDate(
        val date: Long?,
        val isValid: Boolean
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