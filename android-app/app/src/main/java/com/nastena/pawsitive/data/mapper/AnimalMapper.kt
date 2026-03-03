package com.nastena.pawsitive.data.mapper

import com.nastena.pawsitive.data.remote.dto.AnimalResponse
import com.nastena.pawsitive.ui.model.AnimalUi

fun AnimalResponse.toUi(): AnimalUi {
    return AnimalUi(
        id = id,
        name = name,
        subtitle = "$type • $breed • $age лет",
        shelterName = shelterName
    )
}