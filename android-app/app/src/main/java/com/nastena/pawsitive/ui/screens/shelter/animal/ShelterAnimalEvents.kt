package com.nastena.pawsitive.ui.screens.shelter.animal

import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType

sealed interface ShelterAnimalEvents {

    sealed interface Name : ShelterAnimalEvents {

        data class TextUpdated(val newText: String) : Name
    }

    sealed interface Description : ShelterAnimalEvents {

        data class TextUpdated(val newText: String) : Description
    }

    sealed interface Type : ShelterAnimalEvents {

        object ClickedType : Type
        object MenuDismissed : Type

        data class TypeSelected(val type: AnimalType) : Type
    }

    sealed interface Breed : ShelterAnimalEvents {

        object ClickedBreed : Breed
        object MenuDismissed : Breed

        data class BreedSelected(val breed: AnimalBreed) : Breed
    }

    sealed interface Gender : ShelterAnimalEvents {

        object ClickedGender : Gender
        object MenuDismissed : Gender

        data class GenderSelected(val gender: AnimalGender) : Gender
    }

    sealed interface BirthDate : ShelterAnimalEvents {

        data class DateSelected(val date: Long) : BirthDate
    }

    sealed interface Photos: ShelterAnimalEvents {
        data class AddAnimalPhotos(val uri: String) : Photos
        data class AddPassportAnimalPhotos(val uri: String) : Photos

        data class RemoveAnimalPhotos(val uri: String) : Photos

        data class RemovePassportAnimalPhotos(val uri: String) : Photos
    }

    object CancelClicked : ShelterAnimalEvents

    object SaveChangeClicked : ShelterAnimalEvents
}