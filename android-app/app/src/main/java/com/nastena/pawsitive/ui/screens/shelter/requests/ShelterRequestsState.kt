package com.nastena.pawsitive.ui.screens.shelter.requests

object ShelterRequestsState {

    data class Form(
        val animalName: String,
        val userName: String,
        val photoUrls: List<String> = emptyList()
    )
}