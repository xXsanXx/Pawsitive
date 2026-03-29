package com.nastena.pawsitive.ui.common.localization

import androidx.compose.runtime.Composable
import com.nastena.pawsitive.R
import com.nastena.pawsitive.dto.AnimalType

object LocalizationUtils {

    @Composable
    fun getAnimalTypeStringId(type: AnimalType) : Int {
        return when(type) {
            AnimalType.DOG -> R.string.animal_type_dog
            AnimalType.CAT -> R.string.animal_type_cat
        }
    }
}