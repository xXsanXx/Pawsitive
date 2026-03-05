package com.nastena.pawsitive.legacy_ui.user.home

import com.nastena.pawsitive.legacy_ui.model.AnimalUi

sealed class UserHomeState {
    object Loading : UserHomeState()
    data class Success(val animals: List<AnimalUi>) : UserHomeState()
    data class Error(val message: String) : UserHomeState()
}