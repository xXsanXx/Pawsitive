package com.nastena.pawsitive.ui.common.navigation

import kotlin.reflect.KClass

sealed interface Navigation {

    data class To(val route: NavigationRoute, val popUpType: PopUpType = PopUpType.None) : Navigation {
        sealed interface PopUpType {
            object None : PopUpType
            data class Route(val route: KClass<*>) : PopUpType
            object Origin : PopUpType
        }

    }

    object Back : Navigation
}