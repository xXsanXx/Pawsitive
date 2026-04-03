package com.nastena.pawsitive.ui.screens.shelter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class ShelterHomeViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _filesRepository: FilesRepository,
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShelterHomeViewModel(_mainViewModel, _shelterRepository, _filesRepository) as T
    }
}