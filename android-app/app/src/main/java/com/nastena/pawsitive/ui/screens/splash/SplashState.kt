package com.nastena.pawsitive.ui.screens.splash

sealed interface SplashState {
    object Loading : SplashState
    object Ready : SplashState
}