package com.nastena.pawsitive.ui.screens.user.details.form

sealed interface FormEvents {

    sealed interface FullName : FormEvents {

        data class TextUpdated(val newText: String) : FullName
    }

    sealed interface BirthDate : FormEvents {

        data class DateSelected(val date: Long) : BirthDate
    }

    sealed interface Profession : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface Phone : FormEvents {

        data class TextUpdated(val newText: String) : Phone
    }

    data class SendForm(val messageIdOnSuccess: Int) : FormEvents
}