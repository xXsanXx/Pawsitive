package com.nastena.pawsitive.ui.screens.shelter.requests.details

sealed interface ShelterDetailsRequestsEvents {

    object ApprovedClicked : ShelterDetailsRequestsEvents

    object RejectedClicked : ShelterDetailsRequestsEvents

}