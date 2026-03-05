package com.nastena.pawsitive.legacy_ui.shelter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AnimalRepository

class ShelterHomeViewModelFactory(
    private val repository: AnimalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShelterHomeViewModel(repository) as T
    }
}