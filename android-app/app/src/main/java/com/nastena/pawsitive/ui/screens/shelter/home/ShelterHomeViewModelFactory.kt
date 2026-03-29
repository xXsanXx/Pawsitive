package com.nastena.pawsitive.ui.screens.shelter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.AnimalRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class ShelterHomeViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _animalRepository: AnimalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShelterHomeViewModel(_mainViewModel, _animalRepository) as T
    }
}