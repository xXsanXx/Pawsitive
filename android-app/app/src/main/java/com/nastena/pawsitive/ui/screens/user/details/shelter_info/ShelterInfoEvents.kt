package com.nastena.pawsitive.ui.screens.user.details.shelter_info

interface ShelterInfoEvents {

    data class BackToDetailsClicked(
        val animalId: Long
    ) : ShelterInfoEvents
}