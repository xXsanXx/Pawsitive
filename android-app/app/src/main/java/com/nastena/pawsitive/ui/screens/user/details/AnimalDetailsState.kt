package com.nastena.pawsitive.ui.screens.user.details

import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType

object AnimalDetailsState {

    data class Animal(
        val name: String,
        val type: AnimalType,
        val breed: AnimalBreed,
        val gender: AnimalGender,
        val birthDate: Long,
        val photosUrl: List<String> = emptyList()
    )
}