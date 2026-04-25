package com.nastena.pawsitive.ui.screens.shelter.requests

import com.nastena.pawsitive.dto.AdoptionStatus

object ShelterRequestsState {

    data class Form(
        val animalName: String,
        val userName: String,
        val photoUrls: List<String> = emptyList(),
        val status: AdoptionStatus
    )
}