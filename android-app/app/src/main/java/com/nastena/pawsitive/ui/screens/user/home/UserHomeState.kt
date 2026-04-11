package com.nastena.pawsitive.ui.screens.user.home

import com.nastena.pawsitive.dto.AnimalType

object UserHomeState {

    data class Animal(
        val name: String,
        val type: AnimalType,
        val age: Int,
        val photoUrl: String?
    )

}