package com.nastena.pawsitive.ui.screens.shelter.profile

import com.nastena.pawsitive.ui.screens.register.RegisterViewEvents
import com.nastena.pawsitive.ui.screens.user.profile.UserProfileViewEvents

sealed interface ShelterProfileEvents {
    object EditingClicked : ShelterProfileEvents
    object LogoutClicked : ShelterProfileEvents
}