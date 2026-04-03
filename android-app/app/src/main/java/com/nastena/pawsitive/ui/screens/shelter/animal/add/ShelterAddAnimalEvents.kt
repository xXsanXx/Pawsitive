package com.nastena.pawsitive.ui.screens.shelter.animal.add

import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.ui.screens.shelter.editing.EditingShelterProfileEvents

sealed interface ShelterAddAnimalEvents {

    sealed interface Name : ShelterAddAnimalEvents {

        data class TextUpdated(val newText: String) : Name
    }

    sealed interface Description : ShelterAddAnimalEvents {

        data class TextUpdated(val newText: String) : Description
    }

    sealed interface Type : ShelterAddAnimalEvents {

        object ClickedType : Type
        object MenuDismissed : Type

        data class TypeSelected(val type: AnimalType) : Type
    }

    sealed interface Breed : ShelterAddAnimalEvents {

        object ClickedBreed : Breed
        object MenuDismissed : Breed

        data class BreedSelected(val breed: AnimalBreed) : Breed
    }

    sealed interface Gender : ShelterAddAnimalEvents {

        object ClickedGender : Gender
        object MenuDismissed : Gender

        data class GenderSelected(val gender: AnimalGender) : Gender
    }

    sealed interface BirthDate : ShelterAddAnimalEvents {

        data class DateSelected(val date: Long) : BirthDate
    }

    sealed interface Photos: ShelterAddAnimalEvents {
        data class AddAnimalPhotos(val uri: String) : Photos
        data class AddPassportAnimalPhotos(val uri: String) : Photos

        data class RemoveAnimalPhotos(val uri: String) : Photos

        data class RemovePassportAnimalPhotos(val uri: String) : Photos
    }

    object AddClicked : ShelterAddAnimalEvents

    object CancelClicked : ShelterAddAnimalEvents
}