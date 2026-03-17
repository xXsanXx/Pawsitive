package com.nastena.pawsitive.ui.screens.user.profile

import com.nastena.pawsitive.ui.screens.register.RegisterViewEvents

sealed interface UserProfileViewEvents {
    object EditClicked : UserProfileViewEvents
    data class NameChanged(val value: String) : UserProfileViewEvents
    data class DescriptionChanged(val value: String) : UserProfileViewEvents
    object SaveClicked : UserProfileViewEvents
    object LogoutClicked : UserProfileViewEvents
}