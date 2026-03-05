package com.nastena.pawsitive.legacy_ui.model

import com.nastena.pawsitive.legacy_data.remote.dto.AnimalResponse

data class AnimalUi(
    val id: Long,
    val name: String,
    val subtitle: String,
    val shelterName: String
) {
    companion object {
        fun fromResponse(response: AnimalResponse): AnimalUi =
            AnimalUi(
                id = response.id,
                name = response.name,
                subtitle = "${response.type} • ${response.breed} • ${response.age} лет",
                shelterName = response.shelterName
            )
        }
}