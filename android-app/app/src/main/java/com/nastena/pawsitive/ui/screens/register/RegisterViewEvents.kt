package com.nastena.pawsitive.ui.screens.register

import com.nastena.pawsitive.dto.AccountRole

sealed interface RegisterViewEvents {

    sealed interface Name : RegisterViewEvents {

        data class TextUpdated(val newText: String) : Name
    }
    sealed interface Email : RegisterViewEvents {

        data class TextUpdated(val newText: String) : Email
    }

    sealed interface Password : RegisterViewEvents {

        data class TextUpdated(val newText: String) : Password
    }

    sealed interface ConfirmPassword : RegisterViewEvents {

        data class TextUpdated(val newText: String) : ConfirmPassword
    }

    sealed interface AccountRoleMenu : RegisterViewEvents {
        object ClickedMenu : AccountRoleMenu
        object MenuDismissed : AccountRoleMenu

        data class ClickedSelection(val accountRole: AccountRole) : AccountRoleMenu
    }

    object GoToLoginClicked : RegisterViewEvents
    object RegisterClicked : RegisterViewEvents


}