package com.nastena.pawsitive.ui.screens.user.profile

sealed interface UserProfileEvents {
    object LogoutClicked : UserProfileEvents

    data class CancelRequestClicked(val index: Int) : UserProfileEvents
    data class HideRequestClicked(val index: Int) : UserProfileEvents

    data class GoToAnimalClicked(val index: Int) : UserProfileEvents
}