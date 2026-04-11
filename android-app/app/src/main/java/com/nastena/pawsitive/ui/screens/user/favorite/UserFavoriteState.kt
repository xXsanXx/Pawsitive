package com.nastena.pawsitive.ui.screens.user.favorite

import com.nastena.pawsitive.dto.AnimalType

object UserFavoriteState {

    data class Animal(
        val name: String,
        val type: AnimalType,
        val age: Int,
        val photoUrl: String?
    )

    data class ConfirmAnimalDelete(
        val index: Int,
        val isVisible: Boolean = true
    )

}