package com.nastena.pawsitive.ui.screens.user.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nastena.pawsitive.repository.UserRepository
import com.nastena.pawsitive.ui.main.MainViewModel
import com.nastena.pawsitive.ui.screens.user.favorite.UserFavoriteViewModel


class AnimalDetailsViewModelFactory(
    private val _mainViewModel: MainViewModel,
    private val _userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AnimalDetailsViewModel(_mainViewModel, _userRepository) as T
    }
}