package com.nastena.pawsitive.ui.common.localization

import androidx.compose.runtime.Composable
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AnimalBreed
import com.nastena.pawsitive.dto.AnimalGender
import com.nastena.pawsitive.dto.AnimalType

object LocalizationUtils {

    @Composable
    fun getAnimalTypeStringId(type: AnimalType): Int {
        return when (type) {
            AnimalType.DOG -> R.string.animal_type_dog
            AnimalType.CAT -> R.string.animal_type_cat
        }
    }

    @Composable
    fun getAnimalBreedStringId(breed: AnimalBreed): Int {
        return when (breed) {
            AnimalBreed.METIS -> R.string.add_animal_breed_cat_METIS
            AnimalBreed.LABRADOR_RETRIEVER -> R.string.add_animal_breed_dog_LABRADOR_RETRIEVER
            AnimalBreed.DACHSHUND -> R.string.add_animal_breed_dog_DACHSHUND
            AnimalBreed.SIAMESE -> R.string.add_animal_breed_cat_SIAMESE
        }
    }

    @Composable
    fun getAnimalGenderStringId(gender: AnimalGender): Int {
        return when (gender) {
            AnimalGender.MALE -> R.string.add_animal_gender_male
            AnimalGender.FEMALE -> R.string.add_animal_gender_female
        }
    }
}