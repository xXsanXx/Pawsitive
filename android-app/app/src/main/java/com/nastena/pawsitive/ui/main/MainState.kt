package com.nastena.pawsitive.ui.main

import com.nastena.pawsitive.ui.common.NavigationBars

sealed interface MainState {
    object Idle : MainState
    object Loading : MainState
    data class Error(val throwable: Throwable) : MainState
}

data class NavigationBarState(
    val isVisible: Boolean,
    val selected: Int,
    val settings: NavigationBars.Settings
)