package com.nastena.pawsitive.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.ui.graphics.vector.ImageVector
import com.nastena.pawsitive.dto.AccountRole

object NavigationBars {

    data class Settings(
        val initialSelected: Int,
        val items: List<Item>
    )

    data class Item(
        val navigation: Navigation,
        val icon: ImageVector
    )

    val EMPTY = Settings(0, listOf())
    val USER = Settings(
        initialSelected = 0,
        items = listOf(
            Item(
                navigation = Navigation.To(NavigationRoutes.USER_HOME),
                icon = Icons.Default.Home
            ),
            Item(
                navigation = Navigation.To(NavigationRoutes.USER_PROFILE),
                icon = Icons.Default.AccountCircle
            ),
            Item(
                navigation = Navigation.To(NavigationRoutes.FAVORITE),
                icon = Icons.Default.Favorite
            )
        )
    )

    fun fromAccountRole(role: AccountRole) =
        when (role) {
            AccountRole.USER -> USER
            AccountRole.SHELTER -> TODO()
        }
}