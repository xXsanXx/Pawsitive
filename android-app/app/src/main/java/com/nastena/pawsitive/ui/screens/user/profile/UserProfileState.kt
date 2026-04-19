package com.nastena.pawsitive.ui.screens.user.profile

import com.nastena.pawsitive.dto.AdoptionStatus

object UserProfileState {

    data class Requests(
        val animalName: String,
        val shelterName: String,
        val status: AdoptionStatus
    )
}