package com.nastena.pawsitive.ui.main

sealed interface MainState {
    object Idle : MainState
    object Loading : MainState
    data class Error(val throwable: Throwable) : MainState
}