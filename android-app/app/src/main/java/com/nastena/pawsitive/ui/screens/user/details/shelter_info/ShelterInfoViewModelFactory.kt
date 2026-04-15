package com.nastena.pawsitive.ui.screens.user.details.shelter_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.FilesRepository
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.main.MainViewModel

class ShelterInfoViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _userRepository: UserRepository,
    private val _filesRepository: FilesRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShelterInfoViewModel(_mainViewModel, _userRepository, _filesRepository) as T
    }
}
