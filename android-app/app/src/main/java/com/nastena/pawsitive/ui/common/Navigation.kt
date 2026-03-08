package com.nastena.pawsitive.ui.common

sealed interface Navigation {

    data class To(val route: String, val popUpType: PopUpType = PopUpType.None) : Navigation {
        sealed interface PopUpType {
            object None : PopUpType
            data class Route(val route: String) : PopUpType
            object Origin : PopUpType
        }

    }

    object Back : Navigation
}