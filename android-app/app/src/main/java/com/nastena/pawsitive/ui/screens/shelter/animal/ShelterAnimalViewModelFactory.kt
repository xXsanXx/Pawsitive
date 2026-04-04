package com.nastena.pawsitive.ui.screens.shelter.animal

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class ShelterAnimalViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _filesRepository: FilesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShelterAnimalViewModel(_mainViewModel, _shelterRepository, _filesRepository) as T
    }
}