package com.nastena.pawsitive.ui.screens.user.favorite

sealed interface UserFavoriteEvents {

    data class RemoveClicked(val index: Int) : UserFavoriteEvents

    data class GoToAnimalClicked(val index: Int) : UserFavoriteEvents
}