package com.nastena.pawsitive.ui.screens.shelter.requests.details

import com.nastena.pawsitive.dto.AdoptionStatus

object ShelterDetailsRequestsState {

    data class Form(
        val animalName: String,
        val photoUrls: List<String> = emptyList(),

        val userName: String,
        val birthDate: Long,
        val phone: String,
        val profession: String,

        val status: AdoptionStatus

    )


}