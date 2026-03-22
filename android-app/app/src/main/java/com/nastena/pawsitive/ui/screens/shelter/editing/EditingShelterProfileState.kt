package com.nastena.pawsitive.ui.screens.shelter.editing

import com.nastena.pawsitive.ui.common.validation.ValidationState

object EditingShelterProfileState {
    data class Phone(
        val text: String,
        val validation: ValidationState
    )
}