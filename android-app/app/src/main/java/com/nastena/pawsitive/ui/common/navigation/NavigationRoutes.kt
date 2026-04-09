package com.nastena.pawsitive.ui.common.navigation

import com.nastena.pawsitive.dto.AccountRole
import kotlinx.serialization.Serializable

sealed interface NavigationRoute {

    @Serializable
    object Splash : NavigationRoute

    @Serializable
    object Login : NavigationRoute

    @Serializable
    object Register : NavigationRoute

    @Serializable
    object UserHome : NavigationRoute

    @Serializable
    object AnimalDetails : NavigationRoute

    @Serializable
    object UserProfile : NavigationRoute

    @Serializable
    object Favorite : NavigationRoute

    @Serializable
    object ShelterProfile : NavigationRoute

    @Serializable
    object EditingShelterProfile : NavigationRoute

    @Serializable
    object ShelterHome : NavigationRoute

    sealed interface Shelter : NavigationRoute {

        sealed interface Animal : Shelter {
            @Serializable
            object Add : Animal

            @Serializable
            data class Edit(val animalId: Long) : Animal
        }

    }

    companion object {
        fun fromAccountRole(role: AccountRole) = when (role) {
            AccountRole.USER -> NavigationRoute.UserHome
            AccountRole.SHELTER -> NavigationRoute.ShelterHome
        }
    }
}

