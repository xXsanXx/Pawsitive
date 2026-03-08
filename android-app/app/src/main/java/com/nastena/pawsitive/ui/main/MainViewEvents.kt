package com.nastena.pawsitive.ui.main

sealed interface MainViewEvents {
    sealed interface ErrorBox : MainViewEvents {
        object ClickedOk : ErrorBox
    }

    sealed interface NavigationBar : MainViewEvents {
        data class ClickedItem(val index: Int) : NavigationBar
    }
}