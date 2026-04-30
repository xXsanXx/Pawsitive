package com.nastena.pawsitive.ui.main

import com.nastena.pawsitive.ui.screens.BaseScreenViewModel

sealed interface MainViewEvents {

    data class CurrentViewModelChanged(val newCurrentVM: BaseScreenViewModel) : MainViewEvents

    sealed interface ErrorBox : MainViewEvents {
        object ClickedOk : ErrorBox
    }

    sealed interface MessageBox : MainViewEvents {
        object ClickedOk : MessageBox
    }

    sealed interface NavigationBar : MainViewEvents {
        data class ClickedItem(val index: Int) : NavigationBar
    }
}