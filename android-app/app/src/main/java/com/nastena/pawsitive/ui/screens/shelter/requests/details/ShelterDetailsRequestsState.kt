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

        val currentPets: String,
        val previousPets: String,
        val feedingExperience: String,
        val vaccination: String,
        val reason: String,
        val petCareWhenAway: String,
        val problemCharacter: String,
        val healthIssues: String,
        val additionalInfo: String,

        val status: AdoptionStatus

    )

    data class ConfirmDialogState(
        val status: AdoptionStatus
    )


}