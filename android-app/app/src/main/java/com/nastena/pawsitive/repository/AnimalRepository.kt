package com.nastena.pawsitive.repository

import com.nastena.pawsitive.network.api.AnimalApi
import com.nastena.pawsitive.legacy_ui.model.AnimalUi
import kotlin.collections.map

class AnimalRepository(
    private val api: AnimalApi
) {

    suspend fun getAnimals(): List<AnimalUi> {
        return api.getAnimals().map {
            AnimalUi.fromResponse(it)
        }
    }

    suspend fun getMyAnimals(): List<AnimalUi> {
        return api.getMyAnimals().map {
            AnimalUi.fromResponse(it)
        }
    }
}