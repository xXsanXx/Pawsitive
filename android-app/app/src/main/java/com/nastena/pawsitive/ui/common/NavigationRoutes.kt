package com.nastena.pawsitive.ui.common

import com.nastena.pawsitive.dto.AccountRole

object NavigationRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val USER_HOME = "user_home"

    const val USER_PROFILE = "user_profile"

    const val SHELTER_HOME = "shelter_home"

    fun fromAccountRole(role: AccountRole) = when (role) {
        AccountRole.USER -> USER_HOME
        AccountRole.SHELTER -> SHELTER_HOME
    }
}