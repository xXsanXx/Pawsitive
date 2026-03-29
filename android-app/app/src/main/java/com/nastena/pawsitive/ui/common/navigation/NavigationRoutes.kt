package com.nastena.pawsitive.ui.common.navigation

import com.nastena.pawsitive.dto.AccountRole

object NavigationRoutes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val REGISTER = "register"

    const val USER_HOME = "user_home"

    const val USER_PROFILE = "user_profile"

    const val SHELTER_PROFILE = "shelter_profile"

    const val FAVORITE = "favorite"

    const val SHELTER_HOME = "shelter_home"

    const val SHELTER_PROFILE_EDITING = "shelter_profile_editing"

    const val SHELTER_ADD_ANIMAL = "shelter_add_animal"

    fun fromAccountRole(role: AccountRole) = when (role) {
        AccountRole.USER -> USER_HOME
        AccountRole.SHELTER -> SHELTER_HOME
    }
}