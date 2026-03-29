package com.nastena.pawsitive.ui.screens.shelter.animal.add

import com.nastena.pawsitive.ui.common.validation.ValidationState

object ShelterAddAnimalState {

    data class Name(
        val text: String,
        val validation: ValidationState
    )

}