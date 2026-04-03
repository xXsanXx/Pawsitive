package com.nastena.pawsitive.ui.screens.shelter.animal.add

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.ShelterRepository
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.shelter.editing.EditingShelterProfileViewModel

class ShelterAddAnimalViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _shelterRepository: ShelterRepository,
    private val _contentResolver: ContentResolver
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShelterAddAnimalViewModel(_mainViewModel, _shelterRepository, _contentResolver) as T
    }
}