package com.nastena.pawsitive.ui.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.nastena.pawsitive.dto.AccountRole

object NavigationBars {

    data class Settings(
        val initialSelected: Int,
        val items: List<Item>
    )

    data class Item(
        val navigation: Navigation,
        val route: NavigationRoute,
        val icon: ImageVector,
    )

    val EMPTY = Settings(0, listOf())
    val USER = Settings(
        initialSelected = 0,
        items = listOf(
            Item(
                navigation = Navigation.To(NavigationRoute.UserHome),
                route = NavigationRoute.UserHome,
                icon = Icons.Default.Home
            ),
            Item(
                navigation = Navigation.To(NavigationRoute.Favorite),
                route = NavigationRoute.Favorite,
                icon = Icons.Default.Favorite
            ),
            Item(
                navigation = Navigation.To(NavigationRoute.UserProfile),
                route = NavigationRoute.UserProfile,
                icon = Icons.Default.AccountCircle
            )

        )
    )

    val SHELTER = Settings(
        initialSelected = 0,
        items = listOf(
            Item(
                navigation = Navigation.To(NavigationRoute.ShelterHome),
                route = NavigationRoute.ShelterHome,
                icon = Icons.Default.Home
            ),
            Item(
                navigation = Navigation.To(NavigationRoute.ShelterRequests),
                route = NavigationRoute.ShelterRequests,
                icon = Icons.Default.Description
            ),
            Item(
                navigation = Navigation.To(NavigationRoute.ShelterProfile),
                route = NavigationRoute.ShelterProfile,
                icon = Icons.Default.AccountCircle
            )
        )
    )

    fun fromAccountRole(role: AccountRole) =
        when (role) {
            AccountRole.USER -> USER
            AccountRole.SHELTER -> SHELTER
        }
}