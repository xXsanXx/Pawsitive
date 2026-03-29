package com.nastena.pawsitive.ui.screens.shelter.home

import com.nastena.pawsitive.ui.screens.shelter.profile.ShelterProfileEvents

sealed interface ShelterHomeEvents {
    data class EditingClicked(val index: Int) : ShelterHomeEvents
    object AddAnimalClicked : ShelterHomeEvents
}