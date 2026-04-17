package com.nastena.pawsitive.ui.screens.user.details.form

sealed interface FormEvents {

    sealed interface FullName : FormEvents {

        data class TextUpdated(val newText: String) : FullName
    }

    sealed interface Age : FormEvents {

        data class TextUpdated(val newText: String) : Age
    }

    sealed interface Profession : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface Phone : FormEvents {

        data class TextUpdated(val newText: String) : Phone
    }

    object SendForm : FormEvents
}