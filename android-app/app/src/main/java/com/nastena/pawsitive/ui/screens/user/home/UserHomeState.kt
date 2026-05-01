package com.nastena.pawsitive.ui.screens.user.home

import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType

object UserHomeState {

    data class Animal(
        val name: String,
        val type: AnimalType,
        val gender: AnimalGender,
        val breed: AnimalBreed,
        val photoUrl: String?
    )

}