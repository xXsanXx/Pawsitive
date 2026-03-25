package com.nastena.pawsitive.ui.screens.shelter.editing

import androidx.compose.ui.text.input.TextFieldValue
import com.nastena.pawsitive.ui.common.validation.ValidationState

object EditingShelterProfileState {
    data class Phone(
        val text: String,
        val validation: ValidationState
    )
}