package com.nastena.pawsitive.ui.screens.shelter.animal

import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType
import com.nastena.pawsitive.ui.common.validation.ValidationState


object ShelterAnimalState {

    sealed interface Mode {
        object Add : Mode

        data class Edit(
            val idAnimal: Long,
            val originalAnimalPhotos: OriginalPhotos,
            val originalPassportPhotos: OriginalPhotos,
        ) : Mode {
            data class OriginalPhotos(
                val filenames: List<String>,
                val filepath: List<String>
            )
        }
    }

    data class Name(
        val text: String,
        val validation: ValidationState
    )

    data class Type(
        val selected: AnimalType?,
        val isExpended: Boolean,
        val isValid: Boolean,
    )

    data class Breed(
        val selected: AnimalBreed?,
        val options: Set<AnimalBreed>,
        val isExpended: Boolean,
        val isValid: Boolean,
    )

    data class Gender(
        val selected: AnimalGender?,
        val isExpended: Boolean,
        val isValid: Boolean,
    )

    data class BirthDate(
        val date: Long?,
        val isValid: Boolean
    )

    data class Description(
        val text: String,
        val validation: ValidationState
    )

    data class Photos(
        val animal: List<String> = emptyList(),
        val passport: List<String> = emptyList()
    )
}
