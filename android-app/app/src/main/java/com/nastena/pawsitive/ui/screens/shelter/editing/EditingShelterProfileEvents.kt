package com.nastena.pawsitive.ui.screens.shelter.editing

sealed interface EditingShelterProfileEvents {

    sealed interface Phone : EditingShelterProfileEvents {

        data class TextUpdated(val newText: String) : Phone
    }

    sealed interface Address : EditingShelterProfileEvents {

        data class TextUpdated(val newText: String) : Address
    }

    sealed interface Info : EditingShelterProfileEvents {

        data class TextUpdated(val newText: String) : Info
    }

    object SaveChangedClicked : EditingShelterProfileEvents

    object CancelClicked : EditingShelterProfileEvents

}