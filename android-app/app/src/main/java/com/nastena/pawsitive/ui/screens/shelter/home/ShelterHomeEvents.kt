package com.nastena.pawsitive.ui.screens.shelter.home

sealed interface ShelterHomeEvents {
    data class EditingClicked(val index: Int) : ShelterHomeEvents
    object AddAnimalClicked : ShelterHomeEvents

    data class RemoveClicked(val index: Int) : ShelterHomeEvents
}