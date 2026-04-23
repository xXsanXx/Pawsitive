package com.nastena.pawsitive.ui.screens.shelter.profile

sealed interface ShelterProfileEvents {
    object EditingClicked : ShelterProfileEvents
    object LogoutClicked : ShelterProfileEvents
}