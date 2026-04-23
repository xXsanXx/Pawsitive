package com.nastena.pawsitive.ui.screens.user.profile

sealed interface UserProfileEvents {
    object LogoutClicked : UserProfileEvents

    data class CancelRequestClicked(val index: Int) : UserProfileEvents

}