package com.nastena.pawsitive.ui.screens.shelter.home

import com.nastena.pawsitive.dto.AnimalType

object ShelterHomeState {

    data class Animal(
        val name: String,
        val type: AnimalType,
        val age: Int,
        val photoUrls: List<String> = emptyList()
    )

    data class ConfirmAnimalDelete(
        val index: Int,
        val isVisible: Boolean = true
    )

}