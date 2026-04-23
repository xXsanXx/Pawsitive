package com.nastena.pawsitive.ui.screens.shelter.requests

sealed interface ShelterRequestsEvents {

    data class GoToFormClicked(val index: Int) : ShelterRequestsEvents
}