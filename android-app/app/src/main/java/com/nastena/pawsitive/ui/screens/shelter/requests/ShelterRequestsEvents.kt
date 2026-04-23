package com.nastena.pawsitive.ui.screens.shelter.requests

interface ShelterRequestsEvents {

    data class GoToFormClicked(val index: Int) : ShelterRequestsEvents
}