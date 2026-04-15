package com.nastena.pawsitive.ui.screens.user.details.shelter_info

import com.nastena.pawsitive.dto.AnimalType

object ShelterInfoState {

    data class Shelter(
        val name: String,
        val email: String,
        val phone: String,
        val address: String,
        val info: String
    )

    data class Animal(
        val name: String,
        val type: AnimalType,
        val age: Int,
        val photoUrls: List<String> = emptyList()
    )

}