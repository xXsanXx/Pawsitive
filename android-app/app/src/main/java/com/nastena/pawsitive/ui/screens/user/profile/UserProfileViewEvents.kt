package com.nastena.pawsitive.ui.screens.user.profile

sealed interface UserProfileViewEvents {
    object LogoutClicked : UserProfileViewEvents

    data class CancelRequestClicked(val animalId: Long) : UserProfileViewEvents
}