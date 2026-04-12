package com.nastena.pawsitive.ui.screens.user.details

sealed interface AnimalDetailsEvents {

    object GoToFormClicked : AnimalDetailsEvents

    object ShelterInfoClicked : AnimalDetailsEvents


}