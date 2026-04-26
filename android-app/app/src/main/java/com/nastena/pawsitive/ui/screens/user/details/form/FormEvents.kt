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

    sealed interface CurrentPets : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface PreviousPets : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface FeedingExperience : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface Vaccination : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface Reason : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface PetCareWhenAway : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface ProblemCharacter : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface HealthIssues : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface AdditionalInfo : FormEvents {

        data class TextUpdated(val newText: String) : Profession
    }

    sealed interface Phone : FormEvents {

        data class TextUpdated(val newText: String) : Phone
    }

    object SendForm : FormEvents
}