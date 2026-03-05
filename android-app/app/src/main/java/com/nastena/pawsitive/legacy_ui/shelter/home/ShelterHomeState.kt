package com.nastena.pawsitive.legacy_ui.shelter.home

import com.nastena.pawsitive.legacy_ui.model.AnimalUi


sealed class ShelterHomeState {
    object Loading : ShelterHomeState()
    data class Success(val animals: List<AnimalUi>) : ShelterHomeState()
    data class Error(val message: String) : ShelterHomeState()
}
