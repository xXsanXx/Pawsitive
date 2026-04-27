package com.nastena.pawsitive.ui.screens.register

import com.nastena.pawsitive.dto.AccountRole

sealed interface RegisterEvents {

    sealed interface Name : RegisterEvents {

        data class TextUpdated(val newText: String) : Name
    }

    sealed interface Email : RegisterEvents {

        data class TextUpdated(val newText: String) : Email
    }

    sealed interface Password : RegisterEvents {

        data class TextUpdated(val newText: String) : Password
        object EyeClicked : RegisterEvents
    }

    sealed interface ConfirmPassword : RegisterEvents {

        data class TextUpdated(val newText: String) : ConfirmPassword
        object EyeClicked : RegisterEvents
    }

    sealed interface AccountRoleMenu : RegisterEvents {
        object ClickedMenu : AccountRoleMenu
        object MenuDismissed : AccountRoleMenu

        data class ClickedSelection(val accountRole: AccountRole) : AccountRoleMenu
    }

    object GoToLoginClicked : RegisterEvents
    object RegisterClicked : RegisterEvents


}