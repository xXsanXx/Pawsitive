package com.nastena.pawsitive.ui.screens.user.details.form

import com.nastena.pawsitive.ui.common.validation.ValidationState

object FormState {

    data class AnimalInfo(
        val animalName: String,
        val shelterName: String
    )

    data class FullName(
        val text: String,
        val validation: ValidationState
    )

    data class BirthDate(
        val date: Long?,
        val isValid: Boolean
    )

    data class Profession(
        val text: String,
        val validation: ValidationState
    )

    data class CurrentPets(
        val text: String,
        val validation: ValidationState
    )

    data class PreviousPets(
        val text: String,
        val validation: ValidationState
    )

    data class FeedingExperience(
        val text: String,
        val validation: ValidationState
    )

    data class Vaccination(
        val text: String,
        val validation: ValidationState
    )

    data class Reason(
        val text: String,
        val validation: ValidationState
    )

    data class PetCareWhenAway(
        val text: String,
        val validation: ValidationState
    )

    data class ProblemCharacter(
        val text: String,
        val validation: ValidationState
    )

    data class HealthIssues(
        val text: String,
        val validation: ValidationState
    )

    data class AdditionalInfo(
        val text: String,
        val validation: ValidationState
    )

    data class Phone(
        val text: String,
        val validation: ValidationState
    )

}