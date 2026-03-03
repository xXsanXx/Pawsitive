package com.nastena.pawsitive.data.repository

import com.nastena.pawsitive.data.mapper.toUi
import com.nastena.pawsitive.data.remote.api.AnimalApi
import com.nastena.pawsitive.ui.model.AnimalUi
import kotlin.collections.map

class AnimalRepository(
    private val api: AnimalApi
) {

    suspend fun getAnimals(): List<AnimalUi> {
        return api.getAnimals().map {
            it.toUi()
        }
    }

    suspend fun getMyAnimals(): List<AnimalUi> {
        return api.getMyAnimals().map {
            it.toUi()
        }
    }
}