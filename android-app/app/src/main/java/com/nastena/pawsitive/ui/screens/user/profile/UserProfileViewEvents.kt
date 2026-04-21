package com.nastena.pawsitive.ui.screens.user.profile

sealed interface UserProfileViewEvents {
    object LogoutClicked : UserProfileViewEvents

    object CancelClicked : UserProfileViewEvents
}